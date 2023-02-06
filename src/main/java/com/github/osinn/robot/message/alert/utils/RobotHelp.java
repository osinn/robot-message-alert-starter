package com.github.osinn.robot.message.alert.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.github.osinn.robot.message.alert.constant.RobotAlertConstant;
import com.github.osinn.robot.message.alert.ding.dto.DingTalkTextMessage;
import com.github.osinn.robot.message.alert.ding.dto.DingTalkTextMessageContent;
import com.github.osinn.robot.message.alert.ding.dto.DingTalkTextMessageElement;
import com.github.osinn.robot.message.alert.enums.RobotType;
import com.github.osinn.robot.message.alert.fly.dto.*;
import com.github.osinn.robot.message.alert.starter.LoggerAlertAppenderConfigProperties;
import com.github.osinn.robot.message.alert.wx.dto.WxMarkdownMessage;
import com.github.osinn.robot.message.alert.wx.dto.WxMarkdownMessageContent;
import com.github.osinn.robot.message.alert.wx.dto.WxMarkdownMessageElement;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 机器人帮助类
 *
 * @author wency_cai
 */
@Slf4j
public class RobotHelp {

    public static void sendFlyBookWebHookRobotMessage(String loggerName,
                                                      String message,
                                                      Throwable throwable,
                                                      String traceId,
                                                      LoggerAlertAppenderConfigProperties loggingAlertAppenderConfigProperties) {
        String stack = getStack(throwable);

        FlyBookCardMessage cardMessage = new FlyBookCardMessage(new FlyBookCardMessageContent(
                FlyBookCardMessageConfig.DEFAULT,
                new FlyBookCardMessageHeader(
                        new FlyBookCardMessageHeaderTitle(RobotAlertConstant.TITLE),
                        FlyBookCardMessageHeader.ERROR
                ),
                Lists.newArrayList(
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("**服务名称：**" + loggingAlertAppenderConfigProperties.getAppName())
                        ),
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("**链路traceId：**" + traceId)
                        ),
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("**异常时间：**" + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT))
                        ),
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("**异常类名：**" + (throwable == null ? loggerName : throwable.getStackTrace()[0]))
                        ),
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("**异常消息：**" + message)
                        ),
                        new FlyBookCardMessageTextElement(
                                new FlyBookCardMessageTextElementText("异常堆栈：" + stack)
                        )
                )
        ));


        if (StringUtils.isNotBlank(loggingAlertAppenderConfigProperties.getSecret())) {
            try {
                cardMessage.toSign(loggingAlertAppenderConfigProperties.getSecret());
            } catch (Exception e) {
                log.debug(e.getMessage(), e);
            }
        }
        HttpUtils.sendHttpRequest(RobotType.FLY_TALK.getRobotUrl() + loggingAlertAppenderConfigProperties.getToken(), JSONUtil.toJsonStr(cardMessage));
    }

    public static void sendWxWebHookRobotMessage(String loggerName,
                                                 String message,
                                                 Throwable throwable,
                                                 String traceId,
                                                 LoggerAlertAppenderConfigProperties loggingAlertAppenderConfigProperties) {

        String stack = getStack(throwable);

        WxMarkdownMessage wxMarkdownMessage = new WxMarkdownMessage(new WxMarkdownMessageContent(Lists.newArrayList(
                new WxMarkdownMessageElement("### " + RobotAlertConstant.TITLE),
                new WxMarkdownMessageElement("**服务名称：**" + loggingAlertAppenderConfigProperties.getAppName()),
                new WxMarkdownMessageElement("**链路traceId：** " + traceId),
                new WxMarkdownMessageElement("**异常时间：**" + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT)),
                new WxMarkdownMessageElement("**异常类名：**" + (throwable == null ? loggerName : throwable.getStackTrace()[0])),
                new WxMarkdownMessageElement("**异常消息：**" + message),
                new WxMarkdownMessageElement("**异常堆栈：**" + stack)
        )));
        HttpUtils.sendHttpRequest(RobotType.WX_TALK.getRobotUrl() + loggingAlertAppenderConfigProperties.getToken(), JSONUtil.toJsonStr(wxMarkdownMessage));
    }

    private static String getStack(Throwable throwable) {
        String stack = "";
        if (throwable != null) {
            StringWriter stringWriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringWriter, true));
            stack = stringWriter.toString();
        }
        return stack;
    }

    public static void sendDingTalkWebHookRobotMessage(String loggerName, String message, Throwable throwable, String traceId, LoggerAlertAppenderConfigProperties loggingAlertAppenderConfigProperties) {
        String stack = getStack(throwable);
        DingTalkTextMessage dingTalkMessage = new DingTalkTextMessage(new DingTalkTextMessageContent(
                RobotAlertConstant.TITLE,
                Lists.newArrayList(
                        new DingTalkTextMessageElement("### " + RobotAlertConstant.TITLE),
                        new DingTalkTextMessageElement("**服务名称：** " + loggingAlertAppenderConfigProperties.getAppName()),
                        new DingTalkTextMessageElement("**链路traceId：** " + traceId),
                        new DingTalkTextMessageElement("**异常时间：** " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT)),
                        new DingTalkTextMessageElement("**异常类名：** " + (throwable == null ? loggerName : throwable.getStackTrace()[0])),
                        new DingTalkTextMessageElement("**异常消息：** " + message),
                        new DingTalkTextMessageElement("**异常堆栈：** " + stack)
                )

        ));

        Long timestamp = System.currentTimeMillis();
        String sign = timestamp + "\n" + loggingAlertAppenderConfigProperties.getSecret();
        try {
            Mac mac = Mac.getInstance(RobotAlertConstant.HMAC);
            mac.init(new SecretKeySpec(loggingAlertAppenderConfigProperties.getSecret().getBytes(StandardCharsets.UTF_8), RobotAlertConstant.HMAC));
            byte[] signData = mac.doFinal(sign.getBytes(StandardCharsets.UTF_8));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        HttpUtils.sendHttpRequest(String.format(RobotType.DING_TALK.getRobotUrl(), loggingAlertAppenderConfigProperties.getToken(), timestamp, sign), JSONUtil.toJsonStr(dingTalkMessage));
    }

    /**
     * 限制文本描述
     *
     * @param content    内容或问题
     * @param charNumber 长度
     * @return
     */
    public static String limitStrNone(String content, int charNumber) {
        if (StringUtils.isNotBlank(content)) {
            if (content.length() > charNumber) {
                return content.substring(0, charNumber);
            } else {
                return content;
            }
        }
        return "";
    }
}
