package me.heart.com.heartme.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.heart.com.heartme.R;
import me.heart.com.heartme.datamodel.BloodTestConfigDataModel;
import me.heart.com.interfaces.DBHelperListenerInterface;
import me.heart.com.interfaces.ProgressBarInterface;

public class MainFragment extends Fragment {

    public static String MAIN_FRAGMENT_TAG = "MainFragmentFragment";
    public ProgressBarInterface progressBarInterface;
    private DBHelperListenerInterface mCallback;

    private EditText inputTestName;
    private EditText inputTestResut;
    private RelativeLayout goButton;
    private TextView textResponce;
    private ImageView imageResponce;
    private TextView textResponceExplanation;

    ArrayList<BloodTestConfigDataModel> bloodTestConfigDataModelArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        inputTestName = (EditText) view.findViewById(R.id.input_test_name);
        inputTestResut = (EditText) view.findViewById(R.id.input_test_resut);
        goButton = (RelativeLayout) view.findViewById(R.id.go_button);

        textResponce = (TextView) view.findViewById(R.id.text_responce);
        textResponce.setVisibility(View.INVISIBLE);
        imageResponce = (ImageView) view.findViewById(R.id.image_responce);
        imageResponce.setVisibility(View.INVISIBLE);
        textResponceExplanation = (TextView) view.findViewById(R.id.text_responce_explanation);
        textResponceExplanation.setVisibility(View.INVISIBLE);

        bloodTestConfigDataModelArray = new ArrayList<>();
        bloodTestConfigDataModelArray = mCallback.getDBhelper().getBloodTestConfig();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hideSoftKeyboard();

                    if (inputTestName.getText().toString().contains("hdl") || inputTestName.getText().toString().contains("hd")) {
                        int treshold = findTrashold(getResources().getString(R.string.test_result_hdl_colesterol));
                        if (Integer.parseInt(inputTestResut.getText().toString()) >= treshold) {
                            setResults(getResources().getString(R.string.test_result_bad), getResources().getString(R.string.test_result_hdl_colesterol));
                        } else {
                            setResults(getResources().getString(R.string.test_result_good), getResources().getString(R.string.test_result_hdl_colesterol));
                        }
                    } else if (inputTestName.getText().toString().contains("ldl") || inputTestName.getText().toString().contains("ld")) {
                        int treshold = findTrashold(getResources().getString(R.string.test_result_ldl_colesterol));
                        if (Integer.parseInt(inputTestResut.getText().toString()) >= treshold) {
                            setResults(getResources().getString(R.string.test_result_bad), getResources().getString(R.string.test_result_ldl_colesterol));
                        } else {
                            setResults(getResources().getString(R.string.test_result_good), getResources().getString(R.string.test_result_ldl_colesterol));
                        }
                    } else if (inputTestName.getText().toString().contains("a1c") || inputTestName.getText().toString().contains("a1") || inputTestName.getText().toString().contains("1c")) {
                        int treshold = findTrashold(getResources().getString(R.string.test_result_aic));
                        if (Integer.parseInt(inputTestResut.getText().toString()) >= treshold) {
                            setResults(getResources().getString(R.string.test_result_bad), getResources().getString(R.string.test_result_aic));
                        } else {
                            setResults(getResources().getString(R.string.test_result_good), getResources().getString(R.string.test_result_aic));
                        }
                    } else {
                        setResults(getResources().getString(R.string.test_result_unknown), null);
                    }


                }catch (Exception e){

                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        progressBarInterface  = (ProgressBarInterface) activity;
        mCallback = (DBHelperListenerInterface) activity;
        progressBarInterface.getProgressBar().setVisibility(View.GONE);
    }

    private void hideSoftKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int findTrashold(String testType){
        int treshold = 0;
        for(int i=0 ; i<bloodTestConfigDataModelArray.size() ; i++){
            if (bloodTestConfigDataModelArray.get(i).getName().equalsIgnoreCase(getResources().getString(R.string.test_result_hdl_colesterol))){
                treshold = Integer.parseInt(bloodTestConfigDataModelArray.get(i).getThreshold());
                break;
            }else if (bloodTestConfigDataModelArray.get(i).getName().equalsIgnoreCase(getResources().getString(R.string.test_result_ldl_colesterol))){
                treshold =  Integer.parseInt(bloodTestConfigDataModelArray.get(i).getThreshold());
                break;
            }else if (bloodTestConfigDataModelArray.get(i).getName().equalsIgnoreCase(getResources().getString(R.string.test_result_aic))){
                treshold = Integer.parseInt(bloodTestConfigDataModelArray.get(i).getThreshold());
                break;
            }
        }
        return treshold;
    }

    private void setResults(String resultType,String testType){
        switch ( resultType){
            case "Good":
                textResponce.setText(testType);
                textResponce.setVisibility(View.VISIBLE);

                imageResponce.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_very_satisfied));
                imageResponce.setVisibility(View.VISIBLE);

                textResponceExplanation.setText(getResources().getString(R.string.test_result_good));
                textResponceExplanation.setVisibility(View.VISIBLE);
                break;
            case "Bad":
                textResponce.setText(testType);
                textResponce.setVisibility(View.VISIBLE);

                imageResponce.setImageDrawable(getResources().getDrawable(R.drawable.ic_sentiment_very_dissatisfied));
                imageResponce.setVisibility(View.VISIBLE);

                textResponceExplanation.setText(getResources().getString(R.string.test_result_bad));
                textResponceExplanation.setVisibility(View.VISIBLE);
                break;
            case "Unknown":
                textResponce.setVisibility(View.GONE);
                imageResponce.setVisibility(View.GONE);
                textResponceExplanation.setText(getResources().getString(R.string.test_result_unknown));
                textResponceExplanation.setVisibility(View.VISIBLE);
                break;
        }
    }
}
