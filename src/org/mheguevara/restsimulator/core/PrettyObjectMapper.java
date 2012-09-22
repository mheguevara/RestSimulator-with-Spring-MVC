package org.mheguevara.restsimulator.core;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/21/12
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrettyObjectMapper extends ObjectMapper {

    @Override
    public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        jgen.useDefaultPrettyPrinter();
        super.writeValue(jgen, value);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
