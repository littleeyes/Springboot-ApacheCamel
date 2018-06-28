package com.qindaorong.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.qindaorong.framework.components.CamelRouterPool;
import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.dto.MessageDTO;
import com.qindaorong.framework.enums.RouteGroupEnum;
import com.qindaorong.framework.enums.RouteTopicEnum;
import com.qindaorong.framework.routes.HttpRouter;
import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/6/25 15:04
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    CamelRouterPool camelRouterPool;

    @RequestMapping(value = { "/hello" }, method = RequestMethod.POST)
    public String hello(@RequestParam(name = "name")String name ,@RequestParam(name = "age")String age ){
        Map<String,String> map = new HashMap<String, String>(2);
        map.put("name",name);
        map.put("age",age);
        return JSONObject.toJSONString(map);
    }


    @RequestMapping(value = { "/add" }, method = RequestMethod.GET)
    public String add(){
        Map<String,String> paraMap = new HashMap<String, String>(2);
        paraMap.put("name","qindaorong");
        paraMap.put("age","31");

        MessageDTO messageDTO = camelRouterPool.sendNewRequest(RouteTopicEnum.BEI_JING, RouteGroupEnum.USER,paraMap);
        return JSONObject.toJSONString(messageDTO);
    }

    @RequestMapping(value = { "/testcxf" }, method = RequestMethod.GET)
    public MessageDTO testcxf(){
        Map<String,String> paraMap = new HashMap<String, String>(2);
        paraMap.put("name","leo");
        //paraMap.put("address","beijing");
        MessageDTO messageDTO=camelRouterPool.sendCXFRequest(RouteTopicEnum.CXF_BEI_JING, RouteGroupEnum.USER,paraMap);
        return messageDTO;
    }
}
