
# FlatBuffersX
FlatBuffer code generator

generate code[json2flat  flat2json  bean2flat or flat2bean] by java file produce by flatc.

# Feature
- Java Bean to flatbuffers
- Java Bean to Json
- Json to Java Bean
- flatbuffers to Java Bean
# Usage

```gradle
annotationProcessor 'io.flatbufferx:flatbufferx-compiler:last-version"
```
# Require

flatc version >=1.9.0




# usage


```java

public final class UserFB extends JsonMapper<UserFB> {
  public Long id;

  public String login;

  @Override
  public UserFB parse(JsonParser jsonParser) throws IOException {
//...
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
  //....
    return bufferBuilder.dataBuffer();
  }

  @Override
  public int toFlatBufferOffset(FlatBufferBuilder bufferBuilder) throws IOException {
    return User.createUser(bufferBuilder,bufferBuilder.createString(this.login),this.id);
  }

  @Override
  public UserFB flatBufferToBean(Object object) throws IOException {
    User flatObj = (User) object;
    this.id = flatObj.id();
    this.login = flatObj.login();
    return this;
  }
}

```
- flatBufferToBean
   - covert flat object to java bean object
- toFlatBufferOffset
  - append java bean to  flatbuffers,you should not call it directly
- toFlatBuffer
  - convert java bean to flatbuffer for data exchange