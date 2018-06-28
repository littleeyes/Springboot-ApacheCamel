package com.qindaorong.framework.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/22 16:03
 * @Description:
 */
@XmlRootElement(name = "routes")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoutrList {

    @XmlElement(name = "route")
    private List<FrameworkRoute> routeList;

    public List<FrameworkRoute> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<FrameworkRoute> routeList) {
        this.routeList = routeList;
    }
}
