package me.heart.com.heartme.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import me.heart.com.heartme.MainActivity;
import me.heart.com.heartme.datamodel.BloodTestConfigDataModel;
import me.heart.com.heartme.dbhelper.DatabaseHelper;
import me.heart.com.heartme.jsonparser.JsonParser;
import me.heart.com.heartme.network.NetworkHTTPRequests;

public class PullBucketDataService extends IntentService{

    private static NetworkHTTPRequests networkHTTPRequests;
    private static JsonParser jSONparser;
    private DatabaseHelper helper;


    public PullBucketDataService() {
        super("PullBucketData");
    }


    public PullBucketDataService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        networkHTTPRequests = new NetworkHTTPRequests(getApplicationContext());
        jSONparser = new JsonParser();
        helper = MainActivity.helper;

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            if (intent.getStringExtra(PullBucketDataServiceApiKeys.DATA_TYPE_KEY).equalsIgnoreCase(PullBucketDataServiceApiKeys.DATA_TYPE_PULL_BUCKET_DATA)) {
                String getMaterialsResponce = networkHTTPRequests.getBloodTestConfig();
                ArrayList<BloodTestConfigDataModel> bloodTestConfigDataModel = jSONparser.getBloodTestConfigDataModelFromJson(getMaterialsResponce);
                helper.bulkInsertDataToBloodTestConfigDataTable(bloodTestConfigDataModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
