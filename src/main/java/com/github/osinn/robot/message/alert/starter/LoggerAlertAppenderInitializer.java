package com.github.osinn.robot.message.alert.starter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.github.osinn.robot.message.alert.logger.appender.LogbackAlertAppender;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * 日志告警初始
 *
 * @author wency_cai
 */
@Component
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(LoggerAlertAppenderConfigProperties.class)
public class LoggerAlertAppenderInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private LoggerAlertAppenderConfigProperties loggingAlertAppenderConfigProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (loggingAlertAppenderConfigProperties.isEnabled()) {
            log.info("启用日志异常发送告警");
            Assert.notNull(loggingAlertAppenderConfigProperties.getToken(), "日志异常发送告警 token 配置不能为空");
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            LogbackAlertAppender logbackAlertAppender = new LogbackAlertAppender();
            if (loggingAlertAppenderConfigProperties.getLimiter() != null && loggingAlertAppenderConfigProperties.getLimiter().getPermitsPerSecond() != null) {
                logbackAlertAppender.setLimiter(RateLimiter.create(loggingAlertAppenderConfigProperties.getLimiter().getPermitsPerSecond()));
            }
            logbackAlertAppender.setName("ROBOT_MESSAGE_ALERT");
            logbackAlertAppender.setLoggingAlertAppenderConfigProperties(loggingAlertAppenderConfigProperties);
            logbackAlertAppender.setContext(loggerContext);
            logbackAlertAppender.start();
            Logger logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
            logger.addAppender(logbackAlertAppender);
        }
    }
}
