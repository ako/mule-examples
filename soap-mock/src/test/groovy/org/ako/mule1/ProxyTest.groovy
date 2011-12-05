package org.ako.mule1

import org.junit.Test
import org.mule.tck.FunctionalTestCase
import org.mule.module.client.MuleClient
import org.mule.api.MuleMessage
import groovy.xml.MarkupBuilder
import org.junit.Assert

/**
 *
 * @author akoelewijn
 */
class ProxyTest  extends FunctionalTestCase {
    
    @Override
    def protected String getConfigResources() {
        return "mule-config.xml,mock-mule-config.xml"
    }
    
    @Test
    def public void testProxy() {
        
        /*
         * Create soap message
         */
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.'soap:Envelope'('xmlns:soap':"http://schemas.xmlsoap.org/soap/envelope/","xmlns:ako":"http://ako.org"){
            'soap:Header'(){
                
            }
            'soap:Body'(){
                'ako:echo'(){
                    'arg0'("HelloWorld!")
                }
            }
        }
        def message = writer.toString()
        println "message: ${message}"
        
        /*
         * Send message
         */
        MuleClient client = new MuleClient(muleContext)
        MuleMessage result = client.send("http://localhost:8080/localhost:18081/echo", writer.toString(), null)
        println "Payload received: ${result.getPayloadAsString()}"
        
        /*
         * Check
         */
        def expectedResult = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
        '<soap:Body><ns2:echoResponse xmlns:ns2="http://ako.org">'+
        '<return>Hi HelloWorld!</return></ns2:echoResponse></soap:Body></soap:Envelope>'

        Assert.assertEquals(result.getPayloadAsString(),expectedResult)
    }
    
    @Test
    def public void testProxyZipped() {
        
        /*
         * Create soap message
         */
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.'soap:Envelope'('xmlns:soap':"http://schemas.xmlsoap.org/soap/envelope/","xmlns:ako":"http://ako.org"){
            'soap:Header'(){
                
            }
            'soap:Body'(){
                'ako:echo'(){
                    'arg0'("HelloWorld!")
                }
            }
        }
        def message = writer.toString()
        println "message: ${message}"
        
        /*
         * Send message
         */
        MuleClient client = new MuleClient(muleContext)
        MuleMessage result = client.send("http://localhost:8080/localhost:18081/echo-zipped", writer.toString(), null)
        println "Payload received: ${result.getPayloadAsString()}"
                    
        /*
         * Check
         */
        def expectedResult = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
        '<soap:Body><ns2:echoResponse xmlns:ns2="http://ako.org">'+
        '<return>Hi HelloWorld!</return></ns2:echoResponse></soap:Body></soap:Envelope>'

        Assert.assertEquals(result.getPayloadAsString(),expectedResult)
    }

}

