
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.camel.cxf.server;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2018-06-26T17:47:01.454+08:00
 * Generated source version: 3.2.5
 *
 */

@javax.jws.WebService(
                      serviceName = "CamelCXFServiceImplService",
                      portName = "CamelCXFServiceImplPort",
                      targetNamespace = "http://server.cxf.camel.com/",
                      wsdlLocation = "D:/IDEASyswinWorkSpace/framework-camel/wsdl/queryService.wsdl",
                      endpointInterface = "com.camel.cxf.server.CamelCXFServiceInter")

public class CamelCXFServiceImplPortImpl implements CamelCXFServiceInter {

    private static final Logger LOG = Logger.getLogger(CamelCXFServiceImplPortImpl.class.getName());

    /* (non-Javadoc)
     * @see com.camel.cxf.server.CamelCXFServiceInter#queryInfomation(java.lang.String arg0)*
     */
    public java.lang.String queryInfomation(java.lang.String arg0) {
        LOG.info("Executing operation queryInfomation");
        //System.out.println(arg0);
        return arg0;

    }

    /* (non-Javadoc)
     * @see com.camel.cxf.server.CamelCXFServiceInter#sayHello(java.lang.String arg0)*
     */
    public java.lang.String sayHello(java.lang.String arg0) {
        LOG.info("Executing operation sayHello");
        //System.out.println(arg0);
        return "hello";
    }

}
