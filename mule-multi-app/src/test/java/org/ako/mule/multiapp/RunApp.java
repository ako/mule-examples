package org.ako.mule.multiapp;

import org.mule.api.MuleContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;

/**

 */
public class RunApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Mule!");
        DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml,mule-config-1.xml,mule-config-2.xml");
        MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
        muleContext.start();
    }
}
