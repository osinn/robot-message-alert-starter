package com.github.osinn.robot.message.alert.ding.dto;

import com.github.osinn.robot.message.alert.utils.RobotHelp;
import com.github.osinn.robot.message.alert.wx.dto.WxMarkdownMessageElement;
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
public class DingTalkTextMessageContent {

    private String title;

    private List<DingTalkTextMessageElement> text;

    public String getText() {
        if (text == null || text.isEmpty()) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            for (DingTalkTextMessageElement wxMarkdownMessageElement : text) {
                str.append(wxMarkdownMessageElement.getContent()).append("  \n  ");
            }
            return RobotHelp.limitStrNone(str.toString(), 1096);
        }
    }


}
