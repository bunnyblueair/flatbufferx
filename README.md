[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

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

public final class UserFB extends FlatBufferMapper<UserFB> {
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
  
  # performance
  
  |  | FlatBuffers (binary) | Protocol Buffers LITE | Rapid JSON | FlatBuffers (JSON) | pugixml | Raw structs |
  | --- | --- | --- | --- | --- | --- | --- |
  | Decode + Traverse + Dealloc (1 million times, seconds) | 0.08 | 302 | 583 | 105 | 196 | 0.02 |
  | Decode / Traverse / Dealloc (breakdown) | 0 / 0.08 / 0 | 220 / 0.15 / 81 | 294 / 0.9 / 287 | 70 / 0.08 / 35 | 41 / 3.9 / 150 | 0 / 0.02 / 0 |
  | Encode (1 million times, seconds) | 3.2 | 185 | 650 | 169 | 273 | 0.15 |
  | Wire format size (normal / zlib, bytes) | 344 / 220 | 228 / 174 | 1475 / 322 | 1029 / 298 | 1137 / 341 | 312 / 187 |
  | Memory needed to store decoded wire (bytes / blocks) | 0 / 0 | 760 / 20 | 65689 / 4 | 328 / 1 | 34194 / 3 | 0 / 0 |
  | Transient memory allocated during decode (KB) | 0 | 1 | 131 | 4 | 34 | 0 |
  | Generated source code size (KB) | 4 | 61 | 0 | 4 | 0 | 0 |
  | Field access in handwritten traversal code | typed accessors | typed accessors | manual error checking | typed accessors | manual error checking | typed but no safety |
  | Library source code (KB) | 15 | some subset of 3800 | 87 | 43 | 327 | 0 |
