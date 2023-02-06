package com.github.osinn.robot.message.alert.ding.dto;

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
public abstract class DingTalkMessage {

    private String msgtype = "markdown";

}
