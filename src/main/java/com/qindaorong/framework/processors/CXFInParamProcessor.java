package com.qindaorong.framework.processors;

import com.alibaba.fastjson.JSON;
import com.qindaorong.framework.domain.FrameworkRoute;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lisanchuan on 2018/6/28.
 */
public class CXFInParamProcessor  implements Processor {
    private FrameworkRoute frameworkRoute;
    public CXFInParamProcessor(FrameworkRoute frameworkRoute) {
        this.frameworkRoute = frameworkRoute;
    }
    @Override
    public void process(Exchange exchange) throws Exception {
        String jsonstr=JSON.toJSONString(frameworkRoute.getParaMap());
        exchange.getIn().setBody(jsonstr);
    }
}
