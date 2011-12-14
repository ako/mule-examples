package org.ako.mule.xsdvalidation;


import com.ctc.wstx.io.ReaderSource;
import com.ctc.wstx.sr.ValidatingStreamReader;
import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.api.transformer.TransformerException;
import org.mule.module.xml.filters.SchemaValidationFilter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
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

    private boolean isValidStax2(MuleMessage message) throws XMLStreamException, TransformerException {
        // First, let's parse XSD schema object
        XMLValidationSchemaFactory sf = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
        XMLValidationSchema xsd = null;

        try {
            InputStream is = this.getClass().getResourceAsStream("/message.xsd");
            xsd = sf.createSchema(is);
            ValidatingStreamReader vsr = message.getPayload(ValidatingStreamReader.class);
            vsr.validateAgainst(xsd);
            while (vsr.hasNext()) {
                vsr.next();
            }
        } catch (XMLStreamException xe) {
            xe.printStackTrace();
            throw xe;
        }
        return true;
    }
}