package org.ako.mule.xsdvalidation;

import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.routing.filter.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.module.xml.filters.SchemaValidationFilter;

public class XsdValidationFilter extends SchemaValidationFilter {

    public boolean accept(MuleMessage message) {
        logger.info("accept: " + message);
        logger.info("accept payload: " + message.getPayload());
        String runtimeSchemaLocations = message.getSessionProperty("schema.locations");
        logger.info("Runtime schema locations: " + runtimeSchemaLocations);
        super.setSchemaLocations(runtimeSchemaLocations);
        try {
            super.initialise();
            boolean isValid = super.accept(message);
            logger.info("isValid = " + isValid);
            return isValid;
        } catch (InitialisationException e) {
            e.printStackTrace();
        }
        return false;
    }
}