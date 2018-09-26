package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flatbufferx.core.FlatBuffersX;
import io.flatbufferx.core.JsonMapper;

import java.io.IOException;
import java.util.List;

/**
 * Built-in mapper for List objects of an unknown type
 */
public class ListMapper extends JsonMapper<List<Object>> {

    @Override
    public List<Object> parse(JsonParser jsonParser) throws IOException {
        return FlatBuffersX.mapperFor(Object.class).parseList(jsonParser);
    }

    @Override
    public void parseField(List<Object> instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(List<Object> list, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        FlatBuffersX.mapperFor(Object.class).serialize(list, generator);
    }

}
