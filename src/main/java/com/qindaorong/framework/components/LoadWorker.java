package com.qindaorong.framework.components;

import com.qindaorong.framework.dto.MessageDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 15:21
 * @Description:
 */
public class LoadWorker implements Callable<MessageDTO> {

    private String routeId;

    public LoadWorker(String routeId){
        this.routeId = routeId;
    }

    @Override
    public MessageDTO call() throws Exception {
        while (true) {
            if (!StringUtils.isEmpty(CamelRouterPool.map.get(routeId).getResponseBody())) {
                MessageDTO messageDTO = CamelRouterPool.map.get(routeId);
                    return messageDTO;
            }
        }
    }
}
