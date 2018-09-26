package io.flatbufferx.core;


import io.flatbufferx.core.util.SimpleArrayMap;

public interface JsonMapperLoader {

    void putAllJsonMappers(SimpleArrayMap<Class, FlatBufferMapper> map);

    void retainAllClassMapper(SimpleArrayMap<Class, Class> map);

    <T> FlatBufferMapper<T> mapperFor(ParameterizedType<T> type, SimpleArrayMap<ParameterizedType, FlatBufferMapper> partialMappers);


}
