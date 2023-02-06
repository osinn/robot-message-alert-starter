package com.github.osinn.robot.message.alert.fly.dto;

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
public class FlyBookCardMessageConfig {

    public static final FlyBookCardMessageConfig DEFAULT = new FlyBookCardMessageConfig(true, true);

    private boolean wideScreenMode;

    private boolean enableForward;
}
