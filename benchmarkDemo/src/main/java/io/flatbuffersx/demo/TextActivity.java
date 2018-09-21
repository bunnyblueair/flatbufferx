package io.flatbuffersx.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.flatbufferx.core.FlatBuffersX;

import io.flatbuffersx.demo.model.HelloBean;
import io.flatbuffersx.demo.model.HelloList;


public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        HelloList helloList=new HelloList();
        List<HelloBean> list=new ArrayList<>();
        list.add(new HelloBean());
        list.add(new HelloBean());
        helloList.setCode(0);
        helloList.setData(list);
        Log.e("debug", FlatBuffersX.serialize(helloList));
        Log.e("debug", FlatBuffersX.serializeListSimple(list));

    }
}
