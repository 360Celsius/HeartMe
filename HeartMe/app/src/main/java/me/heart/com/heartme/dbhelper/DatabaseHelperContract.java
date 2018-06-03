package me.heart.com.heartme.dbhelper;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Locale;

public class DatabaseHelperContract {
    public static final String packageName = "me.heart.com.heartme";
    public static final String AUTHORITY = packageName + ".provider";
    public static final String CONTENT_BASE = "content://"+AUTHORITY+"/%s";

    private DatabaseHelperContract() {}



    public static class BloodTestConfigDataTable implements BaseColumns {

        public static final String URI_SUFFIX = "blood_test_config_data";
        public static final Uri CONTENT_URI = Uri.parse(String.format(Locale.US,CONTENT_BASE, URI_SUFFIX));

        public static final String TABLE_NAME = "blood_test_config_data";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_THRESHOLD = "threshold";

    }



    public static final String SQL_CREATE_BLOD_TEST_CONFIG_DATA_TABLE =
            "CREATE TABLE " + BloodTestConfigDataTable.TABLE_NAME + " (" +
                    BloodTestConfigDataTable._ID + " INTEGER PRIMARY KEY," +
                    BloodTestConfigDataTable.COLUMN_NAME_NAME + " TEXT," +
                    BloodTestConfigDataTable.COLUMN_NAME_THRESHOLD + " TEXT)";

    public static final String SQL_DELETE_BLOD_TEST_CONFIG_DATA_TABLE =
            "DROP TABLE IF EXISTS " + BloodTestConfigDataTable.TABLE_NAME;

    public static final String SQL_SELECT_BLOD_TEST_CONFIG_DATA_TABLE =
            "SELECT  * FROM " + BloodTestConfigDataTable.TABLE_NAME + " WHERE "+ BloodTestConfigDataTable._ID;



}
