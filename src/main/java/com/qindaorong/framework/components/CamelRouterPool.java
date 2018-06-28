package com.qindaorong.framework.components;

import com.alibaba.fastjson.JSONObject;
import com.camel.cxf.server.Server;
import com.qindaorong.framework.builder.XmlBuilder;
import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.domain.RoutrList;
import com.qindaorong.framework.dto.MessageDTO;
import com.qindaorong.framework.enums.CamelExceptionEnum;
import com.qindaorong.framework.enums.RouteGroupEnum;
import com.qindaorong.framework.enums.RouteTopicEnum;
import com.qindaorong.framework.exceptions.CamelException;
import com.qindaorong.framework.routes.CXFRouter;
import com.qindaorong.framework.routes.HttpRouter;
import org.apache.camel.model.ModelCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @auther: qindaorong
 * @Date: 2018/6/25 16:54
 * @Description:
 */
@Service("camelRouterPool")
public class CamelRouterPool {

    private final Logger logger = LoggerFactory.getLogger(CamelRouterPool.class);

    @Autowired
    private ModelCamelContext camelContext;

    static Map<String, MessageDTO> map = new ConcurrentHashMap<String, MessageDTO>();

    /**
     * route in xml
     */
    private static Map<String,FrameworkRoute> routeMap = new HashMap<String, FrameworkRoute>();

    @PostConstruct
    private void init() {
        try {
            RoutrList routrList = XmlBuilder.loadRoutrs();
            List<FrameworkRoute> frameworkRouteList = routrList.getRouteList();
            constructRouteMap(frameworkRouteList);

            Server server = new Server();
            server.start();
        } catch (Exception e) {
            logger.warn("Load route is failed , Exception Message is {}", e.getMessage());
        } finally {
            logger.info("[fixedThreadPool] has bean init! size is {}", 10);
        }
    }

    public MessageDTO sendNewRequest(RouteTopicEnum topic, RouteGroupEnum group,Map<String,String> paraMap) {
        String mapKey = buildRouteMapKey(topic.getTopic(),group.getGroup());
        if(routeMap.containsKey(mapKey)){
            FrameworkRoute frameworkRoute = routeMap.get(mapKey);

            String paraString = construct2ParaString(paraMap);
            frameworkRoute.setParaMapString(paraString);

            String uuid = UUID.randomUUID().toString();

            MessageDTO messageDTO = new MessageDTO();

            try {
                camelContext.addRoutes(new HttpRouter(frameworkRoute, uuid));
                map.put(uuid, messageDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //start one thread
            messageDTO = this.loadMessageDTO(uuid);

            return messageDTO;
        }else{
            throw new CamelException(CamelExceptionEnum.CAN_NOT_LOAD_ROUTE.getCode(),CamelExceptionEnum.CAN_NOT_LOAD_ROUTE.getExceptionMessage());
        }
    }


    public MessageDTO sendCXFRequest(RouteTopicEnum topic, RouteGroupEnum group,Map<String,String> paraMap) {
        //topic_group
        String mapKey = buildRouteMapKey(topic.getTopic(),group.getGroup());
        if(routeMap.containsKey(mapKey)){
            FrameworkRoute frameworkRoute = routeMap.get(mapKey);
            String uuid = UUID.randomUUID().toString();
            frameworkRoute.setParaMap(paraMap);
            MessageDTO messageDTO = new MessageDTO();
            try {
                camelContext.addRoutes(new CXFRouter(frameworkRoute,uuid));
                map.put(uuid, messageDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageDTO = this.loadMessageDTO(uuid);

            return messageDTO;
        }else{
            throw new CamelException(CamelExceptionEnum.CAN_NOT_LOAD_ROUTE.getCode(),CamelExceptionEnum.CAN_NOT_LOAD_ROUTE.getExceptionMessage());
        }

    }

    public void setMessageDTOByRouteId(String routeId, MessageDTO messageDTO) {
        if (map.containsKey(routeId)) {
            map.put(routeId, messageDTO);
        }
    }

    private void constructRouteMap(List<FrameworkRoute> frameworkRouteList){
        if(!CollectionUtils.isEmpty(frameworkRouteList)){
            for( FrameworkRoute frameworkRoute : frameworkRouteList){
                String topic = frameworkRoute.getTopic();
                String group = frameworkRoute.getGroup();

                String mapKey = buildRouteMapKey(topic,group);

                if(!routeMap.containsKey(mapKey)){
                    routeMap.put(mapKey,frameworkRoute);
                }
            }
        }
    }

    private String buildRouteMapKey(String topic ,String group){
        StringBuffer sb = new StringBuffer();
        sb.append(topic).append("_").append(group);
       return sb.toString().toLowerCase();
    }


    private MessageDTO loadMessageDTO (String routeId){

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        LoadWorker loadWorker = new LoadWorker(routeId);
        FutureTask<MessageDTO> futureTask = new FutureTask<MessageDTO>(loadWorker);
        threadPool.execute(futureTask);

        MessageDTO messageDTO= null ;
        try {
            messageDTO = futureTask.get(3000L, TimeUnit.MILLISECONDS);
            camelContext.removeRoute(routeId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

        return messageDTO;
    }

    private String construct2ParaString(Map<String,String> paraMap){
        StringBuffer sb = new StringBuffer();
        String paraString = "";
        if(!CollectionUtils.isEmpty(paraMap)){
            Iterator<Map.Entry<String,String>> it = paraMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            paraString = sb.toString();
            if(paraString.endsWith("&")){
                paraString = paraString.substring(0,paraString.length()-1);
            }
        }

        return paraString;
    }


}
