package org.ako.mule.xsdvalidation;


import com.ctc.wstx.io.ReaderSource;
import com.ctc.wstx.sr.ValidatingStreamReader;
import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.api.transformer.TransformerException;
import org.mule.module.xml.filters.SchemaValidationFilter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.stax2.validation.*;


public class StreamXsdValidationFilter extends SchemaValidationFilter {

    public boolean accept(MuleMessage message) {
        logger.info("accept: " + message);
        try {
            return isValidStax2(message);
        } catch (Exception xe) {
            xe.printStackTrace();
            //message.setExceptionPayload(new ExceptionPayloader(xe));
            return false;
        }
    }

    private boolean isValidStax2(MuleMessage message) {
        try {
            // First, let's parse XSD schema object
            logger.info("isValidStax2: " + message.getPayload());
            XMLValidationSchemaFactory sf = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
            XMLValidationSchema xsd = null;


            InputStream is = this.getClass().getResourceAsStream("/message.xsd");
            // hier nog caching toevoegen
            xsd = sf.createSchema(is);

            ValidatingStreamReader vsr = message.getPayload(ValidatingStreamReader.class);
            vsr.validateAgainst(xsd);
            while (vsr.hasNext()) {
                vsr.next();
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
            return false;
        }  catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("isValidStax2: true");
        return true;
    }
}