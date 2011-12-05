package org.ako.mule1;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;

/**
 * Hello world!
 *
 */
public class RunMuleServer {

    public static void main(String[] args) throws ConfigurationException, InitialisationException, MuleException {
        System.out.println("Starting Mule!");
        DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml,mock-mule-config.xml");
        MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
        muleContext.start();
    }
}
