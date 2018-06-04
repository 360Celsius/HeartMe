package me.heart.com.heartme.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.heart.com.heartme.datamodel.BloodTestConfigDataModel;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static DatabaseHelper sInstance;
    // Database Info
    private static final String DATABASE_NAME = "heartMeDatabase";
    private static final int DATABASE_VERSION = 2;
    private Context context;


    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseHelperContract.SQL_CREATE_BLOD_TEST_CONFIG_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatabaseHelperContract.SQL_DELETE_BLOD_TEST_CONFIG_DATA_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }



    public void bulkInsertDataToBloodTestConfigDataTable(ArrayList<BloodTestConfigDataModel> bloodTestConfigDataModel){
        deleteMostActiveDataTable();
        ContentValues[] contentsArr = new ContentValues[bloodTestConfigDataModel.size()];
        for(int i=0 ; i<bloodTestConfigDataModel.size() ; i++){
            ContentValues values = new ContentValues();
            values.put(DatabaseHelperContract.BloodTestConfigDataTable.COLUMN_NAME_NAME, bloodTestConfigDataModel.get(i).getName());
            values.put(DatabaseHelperContract.BloodTestConfigDataTable.COLUMN_NAME_THRESHOLD, bloodTestConfigDataModel.get(i).getThreshold());


            contentsArr[i] = values;
        }
        context.getContentResolver().bulkInsert(DatabaseHelperContract.BloodTestConfigDataTable.CONTENT_URI, contentsArr);

    }

    public void deleteMostActiveDataTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DatabaseHelperContract.BloodTestConfigDataTable.TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<BloodTestConfigDataModel> getBloodTestConfig(){

        ArrayList<BloodTestConfigDataModel> bloodTestConfigDataModelArray = new ArrayList<>();

        String queryGetCurrentWEATHER = "SELECT  * FROM " + DatabaseHelperContract.BloodTestConfigDataTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryGetCurrentWEATHER, null);


        try {

            if (cursor.moveToFirst()) {
                do {

                    BloodTestConfigDataModel bloodTestConfigDataModel = new BloodTestConfigDataModel();
                    bloodTestConfigDataModel.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelperContract.BloodTestConfigDataTable.COLUMN_NAME_NAME)));
                    bloodTestConfigDataModel.setThreshold(cursor.getString (cursor.getColumnIndex(DatabaseHelperContract.BloodTestConfigDataTable.COLUMN_NAME_THRESHOLD)));

                    bloodTestConfigDataModelArray.add(bloodTestConfigDataModel);

                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return bloodTestConfigDataModelArray;
    }
}
