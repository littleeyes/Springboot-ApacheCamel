package com.camel.cxf.server;

import javax.xml.ws.Endpoint;

/**
 * Created by lisanchuan on 2018/6/26.
 */
public class Server {
    public void start(){
        String address = "http://localhost:8088/CamelCXFService/queryService";
        Endpoint.publish(address, new CamelCXFServiceImplPortImpl());
        System.out.println("WebService 发布成功 , address : " + address);
    }
}
