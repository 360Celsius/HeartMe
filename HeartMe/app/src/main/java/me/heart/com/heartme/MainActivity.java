package me.heart.com.heartme;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import me.heart.com.heartme.dbhelper.DatabaseHelper;
import me.heart.com.heartme.reciver.BroadCastReciver;
import me.heart.com.heartme.service.PullBucketDataService;
import me.heart.com.heartme.service.PullBucketDataServiceApiKeys;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper helper = null;
    private BroadCastReciver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        }

        helper = DatabaseHelper.getInstance(getApplicationContext());

        Intent msgIntent = new Intent(getApplicationContext(), PullBucketDataService.class);
        msgIntent.putExtra(PullBucketDataServiceApiKeys.DATA_TYPE_KEY, PullBucketDataServiceApiKeys.DATA_TYPE_PULL_BUCKET_DATA);
        startService(msgIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(PullBucketDataService.GET_BLOOD_TEST_CONFIG_DATA);
        receiver =  BroadCastReciver.getInstance();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }
}
