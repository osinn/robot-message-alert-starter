package com.github.osinn.robot.message.alert.wx.dto;

import com.github.osinn.robot.message.alert.logger.appender.LogbackAlertAppender;
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
public class WxMarkdownMessageContent {

    private List<WxMarkdownMessageElement> content;

    public String getContent() {
        if (content == null || content.isEmpty()) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            for (WxMarkdownMessageElement wxMarkdownMessageElement : content) {
                str.append(wxMarkdownMessageElement.getContent()).append("\n");
            }
            return LogbackAlertAppender.limitStackContentLength(str.toString());
        }
    }


}
