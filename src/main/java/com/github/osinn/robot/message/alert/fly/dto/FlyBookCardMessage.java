package com.github.osinn.robot.message.alert.fly.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class  FlyBookCardMessage extends FlyBookMessage {

    private FlyBookCardMessageContent card;

    public FlyBookCardMessage(FlyBookCardMessageContent card) {
        this.card = card;
    }
}