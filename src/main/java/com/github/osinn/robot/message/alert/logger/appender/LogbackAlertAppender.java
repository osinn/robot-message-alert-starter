package com.github.osinn.robot.message.alert.logger.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.osinn.robot.message.alert.constant.RobotAlertConstant;
import com.github.osinn.robot.message.alert.enums.RobotType;
import com.github.osinn.robot.message.alert.starter.LoggerAlertAppenderConfigProperties;
import com.github.osinn.robot.message.alert.utils.RobotHelp;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * logback日志适配器
 *
 * @author wency_cai
 */
@Slf4j
@Setter
public class LogbackAlertAppender extends AppenderBase<ILoggingEvent> {

    private LoggerAlertAppenderConfigProperties loggingAlertAppenderConfigProperties;

    private RateLimiter limiter;


    @Override
    protected void append(ILoggingEvent event) {
        if (!loggingAlertAppenderConfigProperties.getAlertLevel().contains(event.getLevel())) {
            return;
        }
        if (loggingAlertAppenderConfigProperties.getExcludeThrowableClasses() != null && event.getThrowableProxy() != null && event.getThrowableProxy().getClassName().contains(loggingAlertAppenderConfigProperties.getExcludeThrowableClasses())) {
            return;
        }
        if (loggingAlertAppenderConfigProperties.getExcludePackage() != null && event.getLoggerName().contains(loggingAlertAppenderConfigProperties.getExcludePackage())) {
            return;
        }
        String loggerName = event.getLoggerName();
        String message = event.getFormattedMessage();
        Throwable throwable = event.getThrowableProxy() != null ? ((ThrowableProxy) event.getThrowableProxy()).getThrowable() : null;
        String mdcTraceId = event.getMDCPropertyMap().get(RobotAlertConstant.TRACE_ID);

        CompletableFuture.runAsync(() -> {
            if (loggingAlertAppenderConfigProperties.isDebug()) {
                log.info("debug服务异常告警消息，当前时间：{}", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT));
                return;
            }
            if (limiter != null) {
                try {
                    limiter.acquire(loggingAlertAppenderConfigProperties.getLimiter().getAcquire());
                } catch (Exception e) {
                    log.error("服务异常告警限流错误：{}", e.getMessage());
                }
            }
            String traceId = mdcTraceId == null || mdcTraceId.length() == 0 ? "无" : "[" + mdcTraceId + "](" + mdcTraceId + ")";
            if (RobotType.FLY_TALK.equals(loggingAlertAppenderConfigProperties.getRobotType())) {
                RobotHelp.sendFlyBookWebHookRobotMessage(loggerName, message, throwable, traceId, loggingAlertAppenderConfigProperties);
            } else if (RobotType.WX_TALK.equals(loggingAlertAppenderConfigProperties.getRobotType())) {
                RobotHelp.sendWxWebHookRobotMessage(loggerName, message, throwable, traceId, loggingAlertAppenderConfigProperties);
            } else if (RobotType.DING_TALK.equals(loggingAlertAppenderConfigProperties.getRobotType())) {
                RobotHelp.sendDingTalkWebHookRobotMessage(loggerName, message, throwable, traceId, loggingAlertAppenderConfigProperties);
            }
        });

    }
}
