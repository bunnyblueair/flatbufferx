package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flatbufferx.core.JsonMapper;

import java.io.IOException;

/**
 * Built-in mapper for Double objects
 */
public class DoubleMapper extends JsonMapper<Double> {

    @Override
    public Double parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getDoubleValue();
        }
    }

    @Override
    public void parseField(Double instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(Double object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeNumber(object);
    }
}
