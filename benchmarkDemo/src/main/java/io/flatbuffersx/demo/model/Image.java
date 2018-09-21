package io.flatbuffersx.demo.model;

import io.flatbufferx.core.annotation.JsonField;
import io.flatbufferx.core.annotation.JsonObject;

@JsonObject
public class Image {

    @JsonField
    public String id;

    @JsonField
    public String format;

    @JsonField
    public String url;

    @JsonField
    public String description;

}
