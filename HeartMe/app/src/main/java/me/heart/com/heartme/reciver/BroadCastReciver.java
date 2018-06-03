package me.heart.com.heartme.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.heart.com.heartme.R;
import me.heart.com.heartme.fragments.MainFragment;
import me.heart.com.heartme.service.PullBucketDataServiceApiKeys;

import static me.heart.com.heartme.fragments.MainFragment.MAIN_FRAGMENT_TAG;

public class BroadCastReciver extends BroadcastReceiver {


    private static BroadCastReciver _instance;


    private BroadCastReciver() {

    }

    public static BroadCastReciver getInstance() {
        if (_instance == null) {
            _instance = new BroadCastReciver();
        }
        return _instance;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra(PullBucketDataServiceApiKeys.DATA_TYPE_KEY).equalsIgnoreCase(PullBucketDataServiceApiKeys.DATA_TYPE_PULL_BUCKET_DATA)) {

            try {
                FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();

                if (((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.main_view_placeholder) != null) {
                    ft.remove(((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.main_view_placeholder));
                }
                ft.add(R.id.main_view_placeholder, new MainFragment(), MAIN_FRAGMENT_TAG);
                ft.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
