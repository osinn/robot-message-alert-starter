package com.github.osinn.robot.message.alert.enums;

import lombok.Getter;

/**
 * https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=84ffa5d3-ba77-4421-b3b9-b17fccc70fd5
 *
 * @author wency_cai
 */
@Getter
public enum RobotType {

    /**
     * 钉钉
     */
    DING_TALK("钉钉", "https://oapi.dingtalk.com/robot/send?access_token=%s&timestamp=%s&sign=%s"),

    /**
     * 飞书
     */
    FLY_TALK("飞书", "https://open.feishu.cn/open-apis/bot/v2/hook/"),

    /**
     * 企业微信
     */
    WX_TALK("企业微信", "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=");

    private final String text;

    private final String robotUrl;

    RobotType(String text, String robotUrl) {
        this.text = text;
        this.robotUrl = robotUrl;
    }
}
