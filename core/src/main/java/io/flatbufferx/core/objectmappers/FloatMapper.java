package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flatbufferx.core.FlatBufferMapper;

import java.io.IOException;

/**
 * Built-in mapper for Float objects
 */
public class FloatMapper extends FlatBufferMapper<Float> {

    @Override
    public Float parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getFloatValue();
        }
    }

    @Override
    public void parseField(Float instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(Float object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeNumber(object);
    }
}
