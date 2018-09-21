package io.flatbuffersx.demo.parsetasks;

import io.flatbuffersx.demo.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDatabindParser extends Parser {

    private final ObjectMapper objectMapper;

    public JacksonDatabindParser(ParseListener parseListener, String jsonString, ObjectMapper objectMapper) {
        super(parseListener, jsonString);
        this.objectMapper = objectMapper;
    }

    @Override
    protected int parse(String json) {
        try {
            Response response = objectMapper.readValue(json, Response.class);
            return response.users.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            System.gc();
        }
    }

}
