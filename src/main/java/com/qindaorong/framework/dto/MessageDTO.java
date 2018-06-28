package com.qindaorong.framework.dto;

import java.io.Serializable;

/**
 * @auther: qindaorong
 * @Date: 2018/6/22 17:07
 * @Description:
 */
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 4309991981688309458L;

    public MessageDTO() {

    }

    public MessageDTO(String from, String topic, String responseBody) {

        this.from = from;
        this.topic = topic;
        this.responseBody = responseBody;
    }

    private String from;

    private String topic;

    private String responseBody;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
