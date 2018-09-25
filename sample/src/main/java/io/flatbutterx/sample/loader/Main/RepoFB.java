package io.flatbutterx.sample.loader.Main;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.flatbuffers.FlatBufferBuilder;
import io.flatbufferx.core.FlatBuffersX;
import io.flatbufferx.core.JsonMapper;
import io.flatbufferx.core.typeconverters.TypeConverter;
import io.flatbutterx.sample.Repo;
import io.flatbutterx.sample.User;

import java.io.IOException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.nio.ByteBuffer;

@SuppressWarnings("unsafe,unchecked")
public final class RepoFB extends JsonMapper<RepoFB> {
  private static TypeConverter<UserFB> io_flatbutterx_sample_User_type_converter;

  public String description;

  public String fullName;

  public String htmlUrl;

  public Long id;

  public String name;

  public UserFB owner;

  @Override
  public RepoFB parse(JsonParser jsonParser) throws IOException {
    RepoFB instance = new RepoFB();
    if (jsonParser.getCurrentToken() == null) {
      jsonParser.nextToken();
    }
    if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
      jsonParser.skipChildren();
      return null;
    }
    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
      String fieldName = jsonParser.getCurrentName();
      jsonParser.nextToken();
      parseField(instance, fieldName, jsonParser);
      jsonParser.skipChildren();
    }
    return instance;
  }

  @Override
  public void parseField(RepoFB instance, String fieldName, JsonParser jsonParser) throws
      IOException {
    if ("description".equals(fieldName)) {
      instance.description = jsonParser.getValueAsString(null);
    } else if ("fullName".equals(fieldName)) {
      instance.fullName = jsonParser.getValueAsString(null);
    } else if ("htmlUrl".equals(fieldName)) {
      instance.htmlUrl = jsonParser.getValueAsString(null);
    } else if ("id".equals(fieldName)) {
      instance.id = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : Long.valueOf(jsonParser.getValueAsLong());
    } else if ("name".equals(fieldName)) {
      instance.name = jsonParser.getValueAsString(null);
    } else if ("owner".equals(fieldName)) {
      instance.owner = getio_flatbutterx_sample_User_type_converter().parse(jsonParser);
    }
  }

  @Override
  public void serialize(RepoFB object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws
      IOException {
    if (writeStartAndEnd) {
      jsonGenerator.writeStartObject();
    }
    if (object.description != null) {
      jsonGenerator.writeStringField("description", object.description);
    }
    if (object.fullName != null) {
      jsonGenerator.writeStringField("fullName", object.fullName);
    }
    if (object.htmlUrl != null) {
      jsonGenerator.writeStringField("htmlUrl", object.htmlUrl);
    }
    if (object.id != null) {
      jsonGenerator.writeNumberField("id", object.id);
    }
    if (object.name != null) {
      jsonGenerator.writeStringField("name", object.name);
    }
    if (object.owner != null) {
      getio_flatbutterx_sample_User_type_converter().serialize(object.owner, "owner", true, jsonGenerator);
    }
    if (writeStartAndEnd) {
      jsonGenerator.writeEndObject();
    }
  }

  private static final TypeConverter<UserFB> getio_flatbutterx_sample_User_type_converter() {
    if (io_flatbutterx_sample_User_type_converter == null) {
      io_flatbutterx_sample_User_type_converter = FlatBuffersX.typeConverterFor(UserFB.class);
    }
    return io_flatbutterx_sample_User_type_converter;
  }

  @Override
  public ByteBuffer toFlatBuffer(RepoFB object) throws IOException {
    FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();

    Repo.createRepo(bufferBuilder,object.id,bufferBuilder.createString(object.name),bufferBuilder.createString(object.fullName),object.owner.toFlatBufferOffset(bufferBuilder),bufferBuilder.createString(object.htmlUrl),bufferBuilder.createString(object.description));
    return bufferBuilder.dataBuffer();
  }

  @Override
  public int toFlatBufferOffset(FlatBufferBuilder bufferBuilder) throws IOException {
    Repo.createRepo(bufferBuilder,this.id,bufferBuilder.createString(this.name),bufferBuilder.createString(this.fullName),
            this.owner.toFlatBufferOffset(bufferBuilder),bufferBuilder.createString(this.htmlUrl),bufferBuilder.createString(this.description));
    return super.toFlatBufferOffset(bufferBuilder);
  }
}
