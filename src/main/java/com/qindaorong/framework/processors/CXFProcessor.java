package com.qindaorong.framework.processors;

import com.alibaba.fastjson.JSONObject;
import com.qindaorong.framework.components.CamelRouterPool;
import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.dto.MessageDTO;
import com.qindaorong.framework.utils.SpringUtil;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by lisanchuan on 2018/6/27.
 */
public class CXFProcessor implements Processor {
    private final Logger logger = LoggerFactory.getLogger(CXFProcessor.class);
    private FrameworkRoute frameworkRoute;

    public CXFProcessor(FrameworkRoute frameworkRoute) {
        this.frameworkRoute = frameworkRoute;
    }
    @Override
    public void process(Exchange exchange) throws Exception {
        CamelRouterPool camelRouterPool = (CamelRouterPool) SpringUtil.getBean("camelRouterPool");
        DefaultCamelContext camelContext = (DefaultCamelContext) SpringUtil.getBean("camelContext");
        String routeId = exchange.getFromRouteId();

        camelContext.stopRoute(routeId,1L,TimeUnit.SECONDS);

        MessageContentsList bodyStream = (MessageContentsList) exchange.getIn().getBody();
        String responseStr = bodyStream.get(0).toString();
        if (exchange.getPattern() == ExchangePattern.InOnly) {
            Message outMessage = exchange.getOut();
            MessageDTO messageDTO = this.convertToMessageDTO(responseStr);
            outMessage.setBody(JSONObject.toJSONString(messageDTO));
            logger.info("[convertToMessageDTO] is completion. MessageDTO is [{}]", JSONObject.toJSONString(messageDTO));
            camelRouterPool.setMessageDTOByRouteId(routeId, messageDTO);
        }
}
    private MessageDTO convertToMessageDTO(String responseStr) {
        String from = this.frameworkRoute.getFrom();
        String topic = this.frameworkRoute.getTopic();
        String responseBody = responseStr;
        return new MessageDTO(from, topic, responseBody);
    }
}
