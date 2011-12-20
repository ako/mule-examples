package org.ako.mule.multiapp;

import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.ReleasingInputStream;

/**

 */
public class MultiAppTest extends FunctionalTestCase {
    @Test
    public void testmultiApp1() throws MuleException {
        String msg = "HelloWorld!";
        /*
        * send message
        */
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send(
                "http://localhost:8081/app1"
                , msg, null);
        logger.info("result: " + result);
        //ReleasingInputStream ris = result.getPayload(ReleasingInputStream.class);
        //logger.info("result: " + ris.);
    }

    @Test
    public void testmultiApp2() throws MuleException {
        String msg = "HelloWorld!";
        /*
        * send message
        */
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send(
                "http://localhost:8082/app2"
                , msg, null);
        logger.info("result: " + result);
    }
    @Test
    public void testmultiApp1Jms() throws MuleException {
        String msg = "HelloWorld!";
        /*
        * send message
        */
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send(
                "http://localhost:8080/app1"
                , msg, null);
        logger.info("result: " + result);
    }
    @Override
    protected String getConfigResources() {
        return "mule-config.xml,mule-config-1.xml,mule-config-2.xml";
    }
}
