package com.github.osinn.robot.message.alert.fly.dto;

import lombok.Data;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
public class FlyBookCardMessageTextElementText {

    private String tag = "lark_md";

    private String content;

    public FlyBookCardMessageTextElementText(String content) {
        this.content = content;
    }
}
