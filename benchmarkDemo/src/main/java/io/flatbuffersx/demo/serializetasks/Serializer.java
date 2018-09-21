package io.flatbuffersx.demo.serializetasks;

import android.os.AsyncTask;

import io.flatbuffersx.demo.model.Response;

import java.util.concurrent.TimeUnit;

public abstract class Serializer extends AsyncTask<Void, Void, SerializeResult> {

    public interface SerializeListener {
        void onComplete(Serializer serializer, SerializeResult serializeResult);
    }

    private final SerializeListener mParseListener;
    private final Response mResponse;

    protected Serializer(SerializeListener parseListener, Response response) {
        mParseListener = parseListener;
        mResponse = response;
    }

    @Override
    protected SerializeResult doInBackground(Void... params) {
        System.gc();
        long startTime = System.nanoTime();
        serialize(mResponse);
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMicros(endTime - startTime);

        return new SerializeResult(duration, mResponse.users.size());
    }

    @Override
    protected void onPostExecute(SerializeResult parseResult) {
        mParseListener.onComplete(this, parseResult);
    }

    protected abstract String serialize(Response response);
}
