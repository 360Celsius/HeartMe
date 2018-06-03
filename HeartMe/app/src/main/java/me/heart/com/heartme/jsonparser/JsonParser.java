package me.heart.com.heartme.jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.heart.com.heartme.datamodel.BloodTestConfigDataModel;

import static me.heart.com.heartme.jsonparser.JsonParserDict.BLOOD_TEST_CONFIG;
import static me.heart.com.heartme.jsonparser.JsonParserDict.NAME;
import static me.heart.com.heartme.jsonparser.JsonParserDict.THRESHOLD;

public class JsonParser {

    public JsonParser() {

    }



    public ArrayList<BloodTestConfigDataModel> getBloodTestConfigDataModelFromJson(String apiRequestResponce){

        JSONObject readerJSONObject = null;
        JSONArray bloodTestConfigArrayJSONArray = null;
        ArrayList<BloodTestConfigDataModel> bloodTestConfigDataModelList = new ArrayList<>();

        try {
            readerJSONObject = new JSONObject(apiRequestResponce);
            bloodTestConfigArrayJSONArray = readerJSONObject.getJSONArray(BLOOD_TEST_CONFIG);

            for (int i = 0 ; i< bloodTestConfigArrayJSONArray.length() ; i++ ){
                JSONObject listJSONObject = bloodTestConfigArrayJSONArray.getJSONObject(i);

                BloodTestConfigDataModel bloodTestConfigDataModel = new BloodTestConfigDataModel();
                bloodTestConfigDataModel.setName(listJSONObject.getString(NAME));
                bloodTestConfigDataModel.setThreshold(listJSONObject.getString(THRESHOLD));
                bloodTestConfigDataModelList.add(bloodTestConfigDataModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return bloodTestConfigDataModelList;

    }
}
