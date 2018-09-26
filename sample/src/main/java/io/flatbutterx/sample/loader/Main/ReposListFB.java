package io.flatbutterx.sample.loader.Main;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.flatbuffers.FlatBufferBuilder;
import io.flatbufferx.core.FlatBuffersX;
import io.flatbufferx.core.FlatBufferMapper;
import io.flatbufferx.core.typeconverters.TypeConverter;
import io.flatbutterx.sample.ReposList;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unsafe,unchecked")
public final class ReposListFB extends FlatBufferMapper<ReposListFB> {
    private static TypeConverter<RepoFB> io_flatbutterx_sample_Repo_type_converter;

    @Override
    public String toString() {
        return "ReposListFB{" +
                "repos=" + repos +
                ", reposLength=" + reposLength +
                '}';
    }

    public ArrayList<RepoFB> repos;

    public Integer reposLength;

    @Override
    public ReposListFB parse(JsonParser jsonParser) throws IOException {
        ReposListFB instance = new ReposListFB();
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
    public void parseField(ReposListFB instance, String fieldName, JsonParser jsonParser) throws
            IOException {
        if ("repos".equals(fieldName)) {
            if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
                ArrayList<RepoFB> collection1 = new ArrayList<RepoFB>();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    RepoFB value1;
                    value1 = getio_flatbutterx_sample_Repo_type_converter().parse(jsonParser);
                    collection1.add(value1);
                }
                instance.repos = collection1;
            } else {
                instance.repos = null;
            }
        } else if ("reposLength".equals(fieldName)) {
            instance.reposLength = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : Integer.valueOf(jsonParser.getValueAsInt());
        }
    }

    @Override
    public void serialize(ReposListFB object, JsonGenerator jsonGenerator, boolean writeStartAndEnd)
            throws IOException {
        if (writeStartAndEnd) {
            jsonGenerator.writeStartObject();
        }
        final List<RepoFB> lslocalrepos = object.repos;
        if (lslocalrepos != null) {
            jsonGenerator.writeFieldName("repos");
            jsonGenerator.writeStartArray();
            for (RepoFB element1 : lslocalrepos) {
                if (element1 != null) {
                    getio_flatbutterx_sample_Repo_type_converter().serialize(element1, null, false, jsonGenerator);
                }
            }
            jsonGenerator.writeEndArray();
        }
        if (object.reposLength != null) {
            jsonGenerator.writeNumberField("reposLength", object.reposLength);
        }
        if (writeStartAndEnd) {
            jsonGenerator.writeEndObject();
        }
    }

    private static final TypeConverter<RepoFB> getio_flatbutterx_sample_Repo_type_converter() {
        if (io_flatbutterx_sample_Repo_type_converter == null) {
            io_flatbutterx_sample_Repo_type_converter = FlatBuffersX.typeConverterFor(RepoFB.class);
        }
        return io_flatbutterx_sample_Repo_type_converter;
    }

    @Override
    public ByteBuffer toFlatBuffer(ReposListFB object) throws IOException {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();

        //   .final.   ReposList.createReposList(bufferBuilder, 1);
        int offset = toFlatBufferOffset(bufferBuilder);
        bufferBuilder.finish(offset);
        return bufferBuilder.dataBuffer();
    }

    @Override
    public int toFlatBufferOffset(FlatBufferBuilder bufferBuilder) throws IOException {
        int[] data = new int[repos.size()];
        for (int i = 0; i < repos.size(); i++) {
            RepoFB repoFB = repos.get(i);
            data[i] = repoFB.toFlatBufferOffset(bufferBuilder);
         //   ReposList.addRepos(bufferBuilder, bufferBuilder.createByteVector());
        }
       int offset= ReposList.createReposVector(bufferBuilder, data);
       return  ReposList.createReposList(bufferBuilder,offset );

      //  return ReposList.createReposVector(bufferBuilder, data);
        //return super.toFlatBufferOffset(bufferBuilder);
    }

    @Override
    public ReposListFB flatBufferToBean(Object object) throws IOException {
        ReposList reposList= (ReposList) object;
        repos=new ArrayList<>();
        for (int i = 0; i < reposList.reposLength(); i++) {
            RepoFB repoFB=new RepoFB();
            repoFB.flatBufferToBean(reposList.repos(i));
            repos.add(i, repoFB);
        }
        return super.flatBufferToBean(object);
    }
}
