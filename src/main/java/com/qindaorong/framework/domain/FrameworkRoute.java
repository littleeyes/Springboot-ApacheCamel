package com.qindaorong.framework.domain;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/22 15:51
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "route")
public class FrameworkRoute {

    @XmlAttribute(name = "from")
    private String from;

    @XmlAttribute(name = "fromMethod")
    private String fromMethod;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "topic")
    private String topic;

    @XmlAttribute(name = "group")
    private String group;

    private String paraMapString;

    private Map paraMap;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromMethod() {
        return fromMethod;
    }

    public void setFromMethod(String fromMethod) {
        this.fromMethod = fromMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getParaMapString() {
        return paraMapString;
    }

    public void setParaMapString(String paraMapString) {
        this.paraMapString = paraMapString;
    }

    public Map getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map paraMap) {
        this.paraMap = paraMap;
    }
}
