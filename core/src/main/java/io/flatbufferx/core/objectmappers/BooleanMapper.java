package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flatbufferx.core.JsonMapper;

import java.io.IOException;

/**
 * Built-in mapper for Boolean objects
 */
public class BooleanMapper extends JsonMapper<Boolean> {

    @Override
    public Boolean parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getBooleanValue();
        }
    }

    @Override
    public void parseField(Boolean instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(Boolean object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeBoolean(object);
    }
}
