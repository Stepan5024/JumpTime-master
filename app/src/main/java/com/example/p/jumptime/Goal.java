package com.example.p.jumptime;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Goal extends Fragment {


    public Goal() {
    }



    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
               View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        return view;
    }
}