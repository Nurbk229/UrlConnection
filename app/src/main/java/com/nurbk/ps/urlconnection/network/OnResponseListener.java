package com.nurbk.ps.urlconnection.network;

public interface OnResponseListener {

    void onResponse(String response);

    void onError(String error);

}
