package org.ako.mule1;

import java.io.ByteArrayOutputStream;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import org.junit.Test;
import org.mule.tck.FunctionalTestCase;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.NullPayload;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "mock-mule-config.xml";
    }

    @Test
    public void testEchoHttpMock() throws Exception {
        System.out.println("testEchoHttpMock");

        /*
         * Build message
         */
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        SOAPBody soapBody = soapMessage.getSOAPBody();
        soapBody.addBodyElement(new QName("echo"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        soapMessage.writeTo(baos);
        String msg = baos.toString();
        System.out.println("soap message: " + msg);
        
        /*
         * send message
         */
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("http://localhost:18080/echo", msg, null);
        
        /*
         * check result
         */
        System.out.println("message received: " + result.getPayloadAsString());
        assertNotNull(result);
        assertNull(result.getExceptionPayload());
        assertFalse(result.getPayload() instanceof NullPayload);
        //assertEquals("Hi!", result.getPayloadAsString());
    }

    @Test
    public void testEchoJaxWsMock() throws Exception {
        System.out.println("testEchoJaxWsMock");
        //Thread.sleep(100000L);
        /*
         * Build message
         */
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        SOAPBody soapBody = soapMessage.getSOAPBody();
        Name bodyName = soapMessage.getSOAPPart().getEnvelope().createName("echo","ako","http://ako.org");

        soapBody.addBodyElement(bodyName);
        //soapBody.addBodyElement();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        soapMessage.writeTo(baos);
        String msg = baos.toString();
        System.out.println("soap message: " + msg);
        System.out.println("soap part: " + soapMessage.getSOAPPart().getContent());
        /*
         * send message
         */
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("http://localhost:18081/echo", msg, null);
        System.out.println("message received: " + result.getPayloadAsString());
        assertNotNull(result);
        assertNull(result.getExceptionPayload());
        assertFalse(result.getPayload() instanceof NullPayload);
        assertEquals("Hi!", result.getPayloadAsString());
    }
}