package com.qindaorong.framework.routes;

import com.qindaorong.framework.components.CXFParmEntity;
import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.processors.CXFInParamProcessor;
import com.qindaorong.framework.processors.CXFProcessor;
import com.qindaorong.framework.utils.SpringUtil;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lisanchuan on 2018/6/26.
 */

public class CXFRouter extends SpringRouteBuilder {
    private final Logger logger = LoggerFactory.getLogger(CXFRouter.class);

    CXFParmEntity cxfParmEntity = (CXFParmEntity) SpringUtil.getBean("cxfParmEntity");
    private FrameworkRoute frameworkRoute;
    private String routeId;
    public CXFRouter(FrameworkRoute frameworkRoute, String routeId) {
        this.frameworkRoute = frameworkRoute;
        this.routeId = routeId;
    }
    @Override
    public void configure() throws Exception {
       /* CxfComponent cxfComponent = new CxfComponent(getContext());
        CxfEndpoint serviceEndpoint = new CxfEndpoint(SERVICE_ADDRESS, cxfComponent);
        serviceEndpoint.setServiceClass(CamelCXFServiceInter.class);*/

        logger.info("CXFRountUrL is " + frameworkRoute.getFrom() + "?wsdl");

        StringBuffer router_endpoint = new StringBuffer("cxf:")
                .append(frameworkRoute.getFrom())
                .append("?")
                .append(cxfParmEntity.getServiceClass())
                .append("&")
                .append(cxfParmEntity.getWsdlLocation());
                //.append("&dataFormat=POJO");

        from("timer://myTimer?repeatCount=1")
        //from(router_endpoint.toString())
                .routeId(routeId)
                .process(new CXFInParamProcessor(frameworkRoute) )
                .to(router_endpoint.toString())
                .process(new CXFProcessor(frameworkRoute))
               // .to("log:CamelCxfExample?showExchangeId=true").to(serviceEndpoint)
                .end();
    }
}
