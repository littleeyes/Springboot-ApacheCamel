package com.qindaorong.framework.exceptions;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 13:42
 * @Description:
 */
public class CamelException extends RuntimeException  {

    private String code;

    private static final long serialVersionUID = -375805702767069545L;

    public CamelException() { }

    public CamelException(String message) {
        super(message);
    }

    public CamelException(String code, String message) {
        super(message);
        this.code = code;
    }

}
