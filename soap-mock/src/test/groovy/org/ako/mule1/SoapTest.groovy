package org.ako.mule1

import org.junit.Test
import org.mule.tck.FunctionalTestCase
import org.mule.module.client.MuleClient
import org.mule.api.MuleMessage
import groovy.xml.MarkupBuilder

/**
 *
 * @author akoelewijn
 */
class SoapTest  extends FunctionalTestCase {
    
    @Override
    def protected String getConfigResources() {
        return "mock-mule-config.xml"
    }
    
    @Test
    def public void testSoap1() {
        println "Hi"
        
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
        MuleMessage result = client.send("http://localhost:18081/echo", writer.toString(), null)
        
        println "Payload received: ${result.getPayloadAsString()}"
        fail "oops"
    }
}

