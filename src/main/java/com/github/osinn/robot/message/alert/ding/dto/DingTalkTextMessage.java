package com.github.osinn.robot.message.alert.ding.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingTalkTextMessage extends DingTalkMessage {

    private DingTalkTextMessageContent markdown;

    public DingTalkTextMessage(DingTalkTextMessageContent text) {
        this.markdown = text;
    }

}
