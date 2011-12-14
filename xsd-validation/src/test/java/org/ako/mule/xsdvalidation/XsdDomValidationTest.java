package org.ako.mule.xsdvalidation;

import org.mule.tck.FunctionalTestCase;
import org.junit.Test;
import org.junit.Assert;
import org.mule.module.client.MuleClient;
import org.mule.api.MuleMessage;

import java.lang.AssertionError;
import java.lang.Exception;

public class XsdDomValidationTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    @Test
    public void testXsdDomValid() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><Message xmlns='http://ako.org/echo'></Message>";
        MuleMessage result = client.send("vm://xmlDomValidationProxy.in", payload, null);
        Assert.assertNull(result.getExceptionPayload());
        System.out.println("payload: " + result.getPayload());
    }

    @Test
    public void testXsdDomInvalidTag() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><message xmlns='http://ako.org/echo'></message>";
        MuleMessage result = client.send("vm://xmlDomValidationProxy.in", payload, null);
        Assert.assertNotNull(result.getExceptionPayload());
    }

    @Test
    public void testXsdDomInvalidContent() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><Message xmlns='http://ako.org/echo'><Name>Jan<Name></Message>";
        MuleMessage result = client.send("vm://xmlDomValidationProxy.in", payload, null);
        Assert.assertNotNull(result.getExceptionPayload());
    }
}