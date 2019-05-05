package com.gimus.gimus_lib.apiclient;

public interface iAsyncDataReceiver {
    public void onDataReceived(ApiCommand ac, String JSON, ApiObject o);
    public void onDataError(ApiCommand ac, String error);
}
