package org.ako.mule.filtersoapfault;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.logging.Logger;

@WebService(targetNamespace = "http://ako.org/echo", name="EchoService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class EchoService12 {
    private static final Logger logger = Logger.getLogger(EchoService12.class.getName());
    @WebMethod(operationName="echo")
    public String echo(String name){
        logger.info("echo service: " + name);
        return "Hi " + name;
    }
}
