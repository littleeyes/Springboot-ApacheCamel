package com.qindaorong.framework.builder;

import com.qindaorong.framework.domain.FrameworkRoute;
import com.qindaorong.framework.domain.RoutrList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/22 16:06
 * @Description:
 */
public class XmlBuilder {


    final static String DEFAULT_ROUTES_URL = "/camelRoutes/routes_default.xml";

    final static String CUSTOM_ROUTES_URL = "routes.xml";


    /**
     * 加载Routrs
     * @return
     * @throws Exception
     */
     public static RoutrList loadRoutrs () throws Exception {
        JAXBContext jaxbContext;
        RoutrList routrList;

        jaxbContext = JAXBContext.newInstance(RoutrList.class);
        Unmarshaller um = jaxbContext.createUnmarshaller();
        ClassPathResource cpr = new ClassPathResource(CUSTOM_ROUTES_URL);
        if (!cpr.exists()) {
            cpr = new ClassPathResource(DEFAULT_ROUTES_URL);
        }
        routrList = (RoutrList) um.unmarshal(cpr.getInputStream());
        return routrList;
    }

}
