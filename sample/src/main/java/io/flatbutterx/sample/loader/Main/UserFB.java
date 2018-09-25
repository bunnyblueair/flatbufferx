package io.flatbutterx.sample.loader.Main;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.flatbuffers.FlatBufferBuilder;
import io.flatbufferx.core.JsonMapper;
import io.flatbutterx.sample.User;

import java.io.IOException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.nio.ByteBuffer;

@SuppressWarnings("unsafe,unchecked")
public final class UserFB extends JsonMapper<UserFB> {
  public Long id;

  public String login;

  @Override
  public UserFB parse(JsonParser jsonParser) throws IOException {
    UserFB instance = new UserFB();
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
  public void parseField(UserFB instance, String fieldName, JsonParser jsonParser) throws
      IOException {
    if ("id".equals(fieldName)) {
      instance.id = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : Long.valueOf(jsonParser.getValueAsLong());
    } else if ("login".equals(fieldName)) {
      instance.login = jsonParser.getValueAsString(null);
    }
  }

  @Override
  public void serialize(UserFB object, JsonGenerator jsonGenerator, boolean writeStartAndEnd) throws
      IOException {
    if (writeStartAndEnd) {
      jsonGenerator.writeStartObject();
    }
    if (object.id != null) {
      jsonGenerator.writeNumberField("id", object.id);
    }
    if (object.login != null) {
      jsonGenerator.writeStringField("login", object.login);
    }
    if (writeStartAndEnd) {
      jsonGenerator.writeEndObject();
    }
  }

  @Override
  public ByteBuffer toFlatBuffer(UserFB object) throws IOException {
    FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
    User.createUser(bufferBuilder,bufferBuilder.createString(object.login),object.id);
    return bufferBuilder.dataBuffer();
  }

  @Override
  public int toFlatBufferOffset( FlatBufferBuilder bufferBuilder) throws IOException {
   return User.createUser(bufferBuilder,bufferBuilder.createString(this.login),this.id);
   // return super.toFlatBufferOffset(object, bufferBuilder);
  }
}
