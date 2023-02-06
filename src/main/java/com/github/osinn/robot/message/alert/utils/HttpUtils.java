package com.github.osinn.robot.message.alert.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author wency_cai
 */
@Slf4j
public class HttpUtils {

    public static void sendHttpRequest(String webHookUrl, String textMsg) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(webHookUrl);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity se = new StringEntity(textMsg, StandardCharsets.UTF_8);
            httppost.setEntity(se);
            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httppost);
            log.debug("发送消息告警结果：{}", closeableHttpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            log.debug("发送消息告警失败：{}", e.getMessage());
        }
    }

    public static void sendHttpGetRequest(String webHookUrl, String textMsg) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httppost = new HttpGet(webHookUrl);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity se = new StringEntity(textMsg, StandardCharsets.UTF_8);
            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httppost);
            log.debug("发送消息告警结果：{}", closeableHttpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            log.debug("发送消息告警失败：{}", e.getMessage());
        }
    }

//    @Override
//    public String invokeGeneralApi(String url, Map<String, Object> params) {
//        String body = "";
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);
//            ResponseEntity<String> entity = restTemplate.postForEntity(url, httpEntity, String.class);
//            body = entity.getBody();
//            LOGGER.info("invokeGeneralApi()>>>[{}]", body);
//        } catch (RestClientException e) {
//            LOGGER.error("API调用错误，接口地址:[{}]，请求参数:[{}]", url, params, e);
//        }
//        return body;
//    }

//    /**
//     * 推送钉钉机器人消息
//     * @param type
//     * @return
//     */
//    public String sendDingMsg(String type, String orderId, String serviceName) {
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String sign = HmacSha256Util.dingHmacSHA256(timestamp, robotKey);
//        // 钉钉机器人地址（配置机器人的webhook）
//        // https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
//        String dingUrl = "https://oapi.dingtalk.com/robot/send?access_token=" + robotToken + "&timestamp=" + timestamp + "&sign=" + sign;
//
//        //是否通知所有人
//        boolean isAtAll = true;
//
//        //通知具体人的手机号码列表
//        List<String> mobileList = Lists.newArrayList();
//        //mobileList.add("填入手机号，可以具体@到某个人");
//        //mobileList.add("13411111111");
//
//        //钉钉机器人消息内容
//        String content = "【提醒】有一笔新的[" + serviceName + "]服务订单，订单号为" + orderId + "，请及时查看！";
//        //组装请求内容
//        Map<String, Object> reqStr = buildReqStr(content, isAtAll, mobileList);
//        return invokeGeneralApi(dingUrl, reqStr);
//    }
}
