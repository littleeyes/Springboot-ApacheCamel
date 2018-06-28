package com.qindaorong.framework.enums;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 14:51
 * @Description:
 */
public enum RouteGroupEnum {

    /**
     * user
     */
    USER("user"),
    /**
     * houseFound
     */
    HOUSEFOUND("houseFound");

    private String group;

    RouteGroupEnum(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}
