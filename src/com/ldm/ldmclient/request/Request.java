package com.ldm.ldmclient.request;

import android.os.AsyncTask;

/**
 * root abstract request
 * Created by LDM on 13-12-23. Email : nightkid-s@163.com
 */
public abstract class Request<T> {

    protected abstract void preRequest();

    protected abstract T doRequest();

    protected abstract void doRequestComplete(T t);

    private class RequestTask extends AsyncTask<Void, Void, T> {

        @Override
        protected void onPreExecute() {
            preRequest();
            super.onPreExecute();
        }

        @Override
        protected T doInBackground(Void... params) {
            return doRequest();
        }

        @Override
        protected void onPostExecute(T result) {
            doRequestComplete(result);
            super.onPostExecute(result);
        }

    }

    public void startRequest(){
        new RequestTask().execute();
    }
}
