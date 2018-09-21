package io.flatbufferx.core.objectmappers;

import io.flatbufferx.core.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * Built-in mapper for Integer objects
 */
public class IntegerMapper extends JsonMapper<Integer> {

    @Override
    public Integer parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getIntValue();
        }
    }

    @Override
    public void parseField(Integer instance, String fieldName, JsonParser jsonParser) throws IOException { }

    @Override
    public void serialize(Integer object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeNumber(object);
    }
}
