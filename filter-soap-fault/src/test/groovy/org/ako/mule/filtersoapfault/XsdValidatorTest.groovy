package org.ako.mule.filtersoapfault

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
class XsdValidatorTest  extends FunctionalTestCase {
    
    @Override
    def protected String getConfigResources() {
        return "mock-mule-config.xml,mule-config.xml"
    }
    
    @Test
    def public void testSoapValidation() {
        
        /*
         * Create soap message
         */
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.'soap:Envelope'('xmlns:soap':"http://www.w3.org/2003/05/soap-envelope","xmlns:ako":"http://ako.org/echo"){
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
        MuleMessage result = client.send("http://localhost:8080/localhost:18081/echo?xx=yy", writer.toString(), null)
        println "Payload received: ${result.getPayloadAsString()}"
        
        /*
         * Check
         */
        def expectedResult = '<?xml version="1.0" encoding="UTF-8" standalone="no"?><env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope"><env:Header/><env:Body><env:Fault><env:Code><env:Value>env:Sender</env:Value></env:Code><env:Reason><env:Text xml:lang="en-US">invalid message detected</env:Text></env:Reason><env:Role>http://localhost/proxy-thingy</env:Role><env:Detail><Invalid message/></env:Detail></env:Fault></env:Body></env:Envelope>'

        Assert.assertEquals(expectedResult,result.getPayloadAsString())
    }
}
