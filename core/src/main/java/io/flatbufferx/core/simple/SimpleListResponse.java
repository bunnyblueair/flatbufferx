package io.flatbufferx.core.simple;

import java.util.List;

import io.flatbufferx.core.annotation.JsonField;
import io.flatbufferx.core.annotation.JsonObject;

/**
 * Created by bunnyblue on 3/20/18.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)

public class SimpleListResponse  {
    @JsonField
    private  int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    @JsonField

    private List data;
}
