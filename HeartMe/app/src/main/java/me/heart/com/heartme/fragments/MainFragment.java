package me.heart.com.heartme.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.heart.com.heartme.R;
import me.heart.com.interfaces.ProgressBarInterface;

public class MainFragment extends Fragment {

    public static String MAIN_FRAGMENT_TAG = "MainFragmentFragment";
    public ProgressBarInterface progressBarInterface;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        progressBarInterface  = (ProgressBarInterface) activity;
        progressBarInterface.getProgressBar().setVisibility(View.GONE);
    }
}
