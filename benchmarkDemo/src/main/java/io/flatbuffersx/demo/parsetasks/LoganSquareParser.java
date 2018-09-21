package io.flatbuffersx.demo.parsetasks;


import io.flatbuffersx.demo.model.Response;
import io.flatbufferx.core.FlatBuffersX;

public class LoganSquareParser extends Parser {

    public LoganSquareParser(ParseListener parseListener, String jsonString) {
        super(parseListener, jsonString);
    }

    @Override
    protected int parse(String json) {
        try {
            Response response = FlatBuffersX.parse(json, Response.class);
            return response.users.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            System.gc();
        }
    }

}
