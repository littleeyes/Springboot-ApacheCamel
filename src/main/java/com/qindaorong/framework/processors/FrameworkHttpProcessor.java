package com.qindaorong.framework.processors;

import com.alibaba.fastjson.JSON;
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
import org.apache.camel.model.ModelCamelContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/20 16:12
 * @Description:
 */
public class FrameworkHttpProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(FrameworkHttpProcessor.class);

    private CamelRouterPool camelRouterPool;

    private ModelCamelContext camelContext;

    private FrameworkRoute frameworkRoute;

    public FrameworkHttpProcessor(FrameworkRoute frameworkRoute) {
        this.frameworkRoute = frameworkRoute;
    }

    @Override
    public void process(Exchange exchange){
        CamelRouterPool camelRouterPool = (CamelRouterPool) SpringUtil.getBean("camelRouterPool");
        DefaultCamelContext camelContext = (DefaultCamelContext) SpringUtil.getBean("camelContext");
        String routeId = exchange.getFromRouteId();

        try {
            camelContext.stopRoute(routeId,1L,TimeUnit.SECONDS);

            byte[] btArray = (byte[]) exchange.getIn().getBody();
            InputStream sbs = new ByteArrayInputStream(btArray);
            String inputContext = this.analysisMessage(sbs);
            sbs.close();
            logger.info("camel worked!  response message is original is [{}].", inputContext);

            // 存入到exchange的out区域
            if (exchange.getPattern() == ExchangePattern.InOnly) {
                Message outMessage = exchange.getOut();

                //封装DTO返回
                JSONObject jsonObject = JSON.parseObject(inputContext);
                MessageDTO messageDTO = this.convertToMessageDTO(jsonObject);
                outMessage.setBody(JSONObject.toJSONString(messageDTO));
                logger.info("[convertToMessageDTO] is completion. MessageDTO is [{}]", JSONObject.toJSONString(messageDTO));

                camelRouterPool.setMessageDTOByRouteId(routeId, messageDTO);
            }
        } catch (Exception e) {
            logger.info("Exception is [{}].", e);
        }
    }

    /**
     * 从stream中分析字符串内容
     *
     * @param bodyStream
     * @return
     */
    private String analysisMessage(InputStream bodyStream) throws IOException {
        String responseStr = IOUtils.toString(bodyStream, "UTF-8");
        return responseStr;
    }

    private MessageDTO convertToMessageDTO(JSONObject jsonObject) {
        String from = this.frameworkRoute.getFrom();
        String topic = this.frameworkRoute.getTopic();
        String responseBody = jsonObject.toJSONString();
        return new MessageDTO(from, topic, responseBody);
    }
}
