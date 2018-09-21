package io.flatbuffersx.demo.model;

import io.flatbufferx.core.annotation.JsonField;
import io.flatbufferx.core.annotation.JsonObject;

@JsonObject
public class Name {

    @JsonField
    public String first;

    @JsonField
    public String last;
}
