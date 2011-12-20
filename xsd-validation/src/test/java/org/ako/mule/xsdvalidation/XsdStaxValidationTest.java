package org.ako.mule.xsdvalidation;

import com.ctc.wstx.sr.ValidatingStreamReader;
import org.codehaus.stax2.XMLStreamReader2;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XsdStaxValidationTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    @Test
    public void testXsdStaxValid() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><Message xmlns='http://ako.org/echo'>x</Message>";
        MuleMessage result = client.send("vm://xmlStaxValidationProxy.in", payload, null);
        Assert.assertNull(result.getExceptionPayload());
        System.out.println("payload: " + result.getPayload());
    }

    @Test
    public void testXsdStaxInvalidTag() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><message xmlns='http://ako.org/echo'></message>";
        MuleMessage result = client.send("vm://xmlStaxValidationProxy.in", payload, null);
        Assert.assertNotNull(result.getExceptionPayload());
    }

    @Test
    public void testXsdStaxInvalidContent() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><Message xmlns='http://ako.org/echo'><Name>Jan<Name></Message>";
        MuleMessage result = client.send("vm://xmlStaxValidationProxy.in", payload, null);
        Assert.assertNotNull(result.getExceptionPayload());
    }
}