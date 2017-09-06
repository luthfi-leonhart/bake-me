package com.upwardproject.bakeme.util.network;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public abstract class JsonDataReceiver extends JsonHttpResponseHandler implements DataCallbackListener {
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        onLoadingSucceed(response);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        onLoadingSucceed(responseString);
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        onLoadingSucceed(response);
    }

    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
        onLoadingFailed(response != null ? response.toString() : null);
    }

    public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
        onLoadingFailed(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        onLoadingFailed(errorResponse != null ? errorResponse.toString() : null);
    }

}
