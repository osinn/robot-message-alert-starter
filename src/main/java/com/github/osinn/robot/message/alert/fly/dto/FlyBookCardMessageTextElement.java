package com.github.osinn.robot.message.alert.fly.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlyBookCardMessageTextElement extends FlyBookCardMessageElement {

    private String tag = "div";

    private final FlyBookCardMessageTextElementText text;

    public FlyBookCardMessageTextElement(FlyBookCardMessageTextElementText text) {
        super("div");
        this.text = text;
    }
}
