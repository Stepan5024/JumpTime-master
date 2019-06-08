package com.example.p.jumptime.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.p.jumptime.R;


/*
* Домашний экран приветствия. На нем расоложены кноки быстрого перехода в различные части приложения: добавить задачу, текущие планы мечта
*
* */
public class Home extends Fragment {
    View view;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView butAdd = view.findViewById(R.id.imageButton);
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddTask();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        ImageView butPlans = view.findViewById(R.id.imageButton3);
        butPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TimePlans();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        ImageView butGoal = view.findViewById(R.id.imageButton2);
        butGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Goal2();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        return view;
    }
}
