package org.ako.mule.xsdvalidation;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.module.xml.filters.SchemaValidationFilter;

public class XsdValidationFilter extends SchemaValidationFilter {
    // private static final Log logger = LogFactory.getLog(Filter.class.getName());

    public String getSchemaLocation() {
        logger.info("getSchemaLocation: "+ schemaLocation);
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        logger.info("setSchemaLocation: "+ schemaLocation);
        this.schemaLocation = schemaLocation;
    }

    private String schemaLocation = null;
    public boolean accept(MuleMessage message) {
        logger.info("accept: " + message);
        String runtimeSchemaLocations =  message.getSessionProperty("schema.locations");
        logger.info("Runtime schema locations: " + runtimeSchemaLocations);
        super.setSchemaLocations(runtimeSchemaLocations);
        return super.accept(message);
    }

}