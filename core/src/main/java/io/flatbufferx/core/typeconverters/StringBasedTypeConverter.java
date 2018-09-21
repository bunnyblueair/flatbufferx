package io.flatbufferx.core.typeconverters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public abstract class StringBasedTypeConverter<T> implements TypeConverter<T> {

    /**
     * Called to convert a String into an object of type T.
     *
     * @param string The String parsed from JSON.
     */
    public abstract T getFromString(String string);

    /**
     * Called to convert a an object of type T into a String.
     *
     * @param object The object being converted.
     */
    public abstract String convertToString(T object);

    @Override
    public T parse(JsonParser jsonParser) throws IOException {
        return getFromString(jsonParser.getValueAsString(null));
    }

    @Override
    public void serialize(T object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (fieldName != null) {
            jsonGenerator.writeStringField(fieldName, convertToString(object));
        } else {
            jsonGenerator.writeString(convertToString(object));
        }
    }

}
