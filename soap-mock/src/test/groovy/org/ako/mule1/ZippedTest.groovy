package org.ako.mule1

import org.junit.Test
import org.mule.tck.FunctionalTestCase
import org.mule.module.client.MuleClient
import org.mule.api.MuleMessage
import groovy.xml.MarkupBuilder
import org.junit.Assert
import org.mule.transformer.compression.GZipUncompressTransformer

/**
 *
 * @author akoelewijn
 */
class ZippedTest  extends FunctionalTestCase {
    
    @Override
    def protected String getConfigResources() {
        return "mock-mule-config.xml"
    }
    
    @Test
    def public void testZipped() {
        
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
        MuleMessage result = client.send("http://localhost:18081/echo-zipped", writer.toString(), null)
        /*
         * Print some results
         */
        println "Properties received: ${result.getPropertyNames()}"
        println "Content-Encoding received: ${result.getInboundProperty('Content-Encoding')}"
        result.getInboundPropertyNames().each(){ propertyName ->
            println "${propertyName} = ${result.getInboundProperty(propertyName)}"
        }
        println "Payload received: ${result.getPayloadAsString()}"

        def transformer = new GZipUncompressTransformer()
        byte[] uncompressedResult = transformer.doTransform(result.getPayloadAsBytes(),null)
        String ucr = new String(uncompressedResult)
        println "uncompressedResult: ${uncompressedResult} ${ucr}"
        /*
         * Check
         */
        def expectedResult = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'+
        '<soap:Body><ns2:echoResponse xmlns:ns2="http://ako.org">'+
        '<return>Hi HelloWorld!</return></ns2:echoResponse></soap:Body></soap:Envelope>'

        Assert.assertEquals(result.getPayloadAsString(),expectedResult)
    }
}

