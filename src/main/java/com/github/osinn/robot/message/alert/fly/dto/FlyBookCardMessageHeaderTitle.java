package com.github.osinn.robot.message.alert.fly.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
public class FlyBookCardMessageHeaderTitle {

    private String tag = "plain_text";

    private String content;

   public FlyBookCardMessageHeaderTitle(String content) {
        this.content = content;
    }
}
