package com.github.osinn.robot.message.alert.fly.dto;

import com.github.osinn.robot.message.alert.constant.RobotAlertConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * 描述
 *
 * @author wency_cai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class FlyBookMessage {

    private Integer timestamp;

    private String sign;

    /**
     * 卡片模式
     */
    private String msg_type = "interactive";

    public void toSign(String secret) throws Exception {
        int time = Integer.parseInt((System.currentTimeMillis() / 1000) + "");
        this.sign = genSign(secret, time);
        this.timestamp = time;
    }

    /**
     * 生成签名
     */
    private String genSign(String secret, int timestamp) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance(RobotAlertConstant.HMAC);
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), RobotAlertConstant.HMAC));
        byte[] signData = mac.doFinal(new byte[]{});
        return new String(Base64.encodeBase64(signData));
    }
}