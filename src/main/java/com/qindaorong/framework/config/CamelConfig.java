package com.qindaorong.framework.config;

import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/22 15:24
 * @Description:
 */
@Configuration
public class CamelConfig {

    private final Logger logger = LoggerFactory.getLogger(CamelConfig.class);

    @Bean
    public ModelCamelContext camelContext() {

        ModelCamelContext camelContext = new DefaultCamelContext();
        try {
            logger.info("[Camel Route] start to init");
            camelContext.setErrorHandlerBuilder(new NoErrorHandlerBuilder());
            camelContext.start();
            logger.info("[Camel Route] load routes completed....");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[Camel Route] has Exception during init , message is {}", e.getMessage());
        }

        logger.info("[Camel Route] end to init .....");
        return camelContext;
    }
}
