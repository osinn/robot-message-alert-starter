package com.github.osinn.robot.message.alert.fly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlyBookCardMessageContent {

    private FlyBookCardMessageConfig config;

    private FlyBookCardMessageHeader header;

    private List<FlyBookCardMessageElement> elements;
}
