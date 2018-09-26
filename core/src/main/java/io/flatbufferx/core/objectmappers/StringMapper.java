package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flatbufferx.core.JsonMapper;

import java.io.IOException;

/**
 * Built-in mapper for String objects
 */
public class StringMapper extends JsonMapper<String> {

    @Override
    public String parse(JsonParser jsonParser) throws IOException {
        return jsonParser.getText();
    }

    @Override
    public void parseField(String instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(String object, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        generator.writeString(object);
    }
}
