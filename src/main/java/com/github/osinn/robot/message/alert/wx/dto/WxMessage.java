package com.github.osinn.robot.message.alert.wx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class WxMessage {

    /**
     * markdown
     */
    private String msgtype = "markdown";
}
