package org.ako.mule.springmuleproperties;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
/**
 * Created by IntelliJ IDEA.
 * User: akoelewijn
 * Date: 12/13/11
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpringPropertiesTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    @Test
    public void testAccessSpringProperty() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        String payload = "HelloWorld!";
        MuleMessage result = client.send("vm://springproperty.in", payload, null);
        Assert.assertNull(result.getExceptionPayload());
    }
}
