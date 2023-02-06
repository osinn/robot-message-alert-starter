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
public class FlyBookCardMessageHeader {

    public static final String ERROR = "red";
    public static final String WARNING = "orange";
    public static final String SUCCESS = "green";
    public static final String PRIMARY = "blue";
    public static final String GREY = "grey";

    private FlyBookCardMessageHeaderTitle title;

    private String template;

    public FlyBookCardMessageHeader(FlyBookCardMessageHeaderTitle title, String template) {
        this.title = title;
        this.template = template;
    }
}
