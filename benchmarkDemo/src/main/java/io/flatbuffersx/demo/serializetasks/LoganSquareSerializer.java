package io.flatbuffersx.demo.serializetasks;


import io.flatbuffersx.demo.model.Response;
import io.flatbufferx.core.FlatBuffersX;

public class LoganSquareSerializer extends Serializer {

    public LoganSquareSerializer(SerializeListener parseListener, Response response) {
        super(parseListener, response);
    }

    @Override
    protected String serialize(Response response) {
        try {
            return FlatBuffersX.serialize(response);
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }
}
