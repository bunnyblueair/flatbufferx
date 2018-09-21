package io.flatbuffersx.demo.model;

import io.flatbufferx.core.annotation.JsonField;
import io.flatbufferx.core.annotation.JsonObject;

@JsonObject
public class Friend {

    @JsonField
    public int id;

    @JsonField
    public String name;
}
