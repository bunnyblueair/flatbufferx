package io.flatbufferx.core.objectmappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flatbufferx.core.FlatBuffersX;
import io.flatbufferx.core.FlatBufferMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Built-in mapper for Map objects of a unknown value types
 */
public class MapMapper extends FlatBufferMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> parse(JsonParser jsonParser) throws IOException {
        return FlatBuffersX.mapperFor(Object.class).parseMap(jsonParser);
    }

    @Override
    public void parseField(Map<String, Object> instance, String fieldName, JsonParser jsonParser) throws IOException {
    }

    @Override
    public void serialize(Map<String, Object> map, JsonGenerator generator, boolean writeStartAndEnd) throws IOException {
        FlatBuffersX.mapperFor(Object.class).serialize(map, generator);
    }

}
