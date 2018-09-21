package io.flatbufferx.core;


import io.flatbufferx.core.util.SimpleArrayMap;

public interface JsonMapperLoader {

    void putAllJsonMappers(SimpleArrayMap<Class, JsonMapper> map);
    void retainAllClassMapper(SimpleArrayMap<Class, Class> map);
    <T> JsonMapper<T> mapperFor(ParameterizedType<T> type, SimpleArrayMap<ParameterizedType, JsonMapper> partialMappers);


}
