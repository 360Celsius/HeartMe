package me.heart.com.heartme.network;

import android.content.Context;

import java.util.ArrayList;

import me.heart.com.heartme.datamodel.BloodTestConfigDataModel;

public class NetworkHTTPRequests {

    public static String SERVER_DOMAIN = "https://s3.amazonaws.com/s3.helloheart.home.assignment/bloodTestConfig.json";

    private static NetworkHTTPConnection networkHTTPConnection;
    private Context ctx;


    public NetworkHTTPRequests(Context ctx) {
        this.networkHTTPConnection = NetworkHTTPConnection.getInstance();
        this.ctx = ctx;
    }


    public String getBloodTestConfig(){
        String responce = null;
        responce = networkHTTPConnection.getHttp(SERVER_DOMAIN);
        return responce;
    }

}
