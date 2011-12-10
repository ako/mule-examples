package org.ako.mule.filtersoapfault;


import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.Locale;
import java.util.Map;

public class ExceptionBeanToSoapFault extends AbstractMessageTransformer {
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
        SOAPMessage soapMessage = null;
        logger.info("transformMessage: " + message);
        try {
            soapMessage = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage();
            SOAPFault soapFault = soapMessage.getSOAPBody().addFault();
            soapFault.setFaultString("oops");
            soapFault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
            soapFault.addFaultReasonText("invalid message detected", Locale.getDefault());
            soapFault.setFaultRole("http://localhost/proxy-thingy");
            soapFault.addDetail().addDetailEntry(new QName("Invalid message"));
            soapMessage.saveChanges();
            message.setPayload(soapMessage.getSOAPPart());
            message.setExceptionPayload(null);
            logger.info("Payload for logging; " + message.getPayloadForLogging());
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Failed to create SOAPFault object");
        }
        logger.info("transformMessage 2: " + message);
        return message;
    }
}
