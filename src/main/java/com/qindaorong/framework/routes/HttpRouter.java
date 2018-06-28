package com.qindaorong.framework.routes;

import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.processors.FrameworkHttpProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.http.common.HttpMethods;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/20 15:56
 * @Description:
 */
public class HttpRouter extends SpringRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(HttpRouter.class);

    private FrameworkRoute frameworkRoute;

    private String routeId;

    final String STRING_JETTY = "jetty:";

    final String STRING_URL_END = "?bridgeEndpoint=true&connectionClose=true";


    public HttpRouter(FrameworkRoute frameworkRoute){
        this.frameworkRoute = frameworkRoute;
    }

    public HttpRouter(FrameworkRoute frameworkRoute,String routeId){
        this.frameworkRoute = frameworkRoute;
        this.routeId = routeId;
    }

    @Override
    public void configure() throws Exception {
        if(!StringUtils.isEmpty(frameworkRoute.getFrom())){
            if(HttpMethods.POST.name().equals(frameworkRoute.getFromMethod().toUpperCase())){
                //定时任务触发
                from("timer://myTimer?repeatCount=1").routeId(routeId)
                        //.to("jetty:http://localhost:8080/hello?bridgeEndpoint=true&connectionClose=true")
                        .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST.name()))
                        .to(convertHttpUrl(frameworkRoute.getFrom(),frameworkRoute.getParaMapString()))
                        .process(new FrameworkHttpProcessor(frameworkRoute))
                        .end();
            }else{
                //定时任务触发
                from("timer://myTimer?repeatCount=1").routeId(routeId)
                        //.to("jetty:http://localhost:8080/hello?bridgeEndpoint=true&connectionClose=true")
                        .to(convertHttpUrl(frameworkRoute.getFrom(),frameworkRoute.getParaMapString()))
                        .process(new FrameworkHttpProcessor(frameworkRoute))
                        .end();
            }
        }else{
            logger.error("watch url is null, please check url in [application.yml]");
        }
    }

    private String convertHttpUrl(String url,String parameterUrl ){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isEmpty(parameterUrl)){
            sb.append(STRING_JETTY).append(url).append(STRING_URL_END);
        }else{
            sb.append(STRING_JETTY).append(url).append("?").append(parameterUrl).append(STRING_URL_END);
        }
        return sb.toString();
    }
}
