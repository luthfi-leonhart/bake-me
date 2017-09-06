package com.upwardproject.bakeme.util.network;

public interface DataCallbackListener {
    void onLoadingSucceed(Object data);

    void onLoadingEmpty(String message);

    void onLoadingFailed(String message);
}
