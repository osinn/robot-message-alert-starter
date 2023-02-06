package com.github.osinn.robot.message.alert.wx.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMarkdownMessage extends WxMessage{

    private WxMarkdownMessageContent markdown;

    public WxMarkdownMessage(WxMarkdownMessageContent markdown) {
        this.markdown = markdown;
    }

}
