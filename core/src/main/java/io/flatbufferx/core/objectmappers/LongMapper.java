package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flatbufferx.core.FlatBufferMapper;

import java.io.IOException;

/**
 * Built-in mapper for Long objects
 */
public class LongMapper extends FlatBufferMapper<Long> {

    @Override
    public Long parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return jsonParser.getLongValue();
        }
    }

    @Override
    public void parseField(Long instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(Long object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeNumber(object);
    }
}
