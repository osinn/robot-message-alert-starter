package com.github.osinn.robot.message.alert.starter;

import ch.qos.logback.classic.Level;
import com.github.osinn.robot.message.alert.enums.RobotType;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * LoggerAlertAppenderConfigProperties
 *
 * @author wency_cai
 */
@Data
@ConfigurationProperties(prefix = LoggerAlertAppenderConfigProperties.PREFIX)
public class LoggerAlertAppenderConfigProperties {

    public final static String PREFIX = "robot.alert.config";

    /**
     * 是否开启
     */
    private boolean enabled;

    /**
     * 是否开启debug，开启后不会发送消息，只会打印当前执行时间日志
     */
    private boolean debug;

    /**
     * webhook的token
     */
    private String token;

    /**
     * 签名密钥
     */
    private String secret;

    /**
     * 发送机器人类型
     */
    private RobotType robotType = RobotType.FLY_TALK;

    /**
     * 告警日志级别
     */
    private List<Level> alertLevel = Lists.newArrayList(Level.ERROR);

    /**
     * 服务名称
     */
    private String appName;

    /**
     * 排除的异常类集合用逗号分隔
     */
    private String excludeThrowableClasses;

    /**
     * 排除的包用逗号分隔
     */
    private String excludePackage;

    /**
     * 基于guava限流配置
     */
    private Limiter limiter;

    @Data
    public static class Limiter {

        /**
         * 限流 每秒执行次数，空不限制
         */
        private Double permitsPerSecond;

        private Integer acquire = 1;
    }


}
