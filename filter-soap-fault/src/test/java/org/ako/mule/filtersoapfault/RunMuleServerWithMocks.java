package org.ako.mule.filtersoapfault;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;

/**
 * Run Mule
 */
public class RunMuleServerWithMocks {
    public static void main(String[] args) throws ConfigurationException, InitialisationException, MuleException {
        System.out.println("Starting Mule!");
        DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = new SpringXmlConfigurationBuilder("mock-mule-config.xml,mule-config.xml");
        MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
        muleContext.start();
    }
}
