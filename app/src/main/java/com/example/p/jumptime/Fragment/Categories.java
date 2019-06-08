package com.example.p.jumptime.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.p.jumptime.R;

/*
 * 4 колеса жизни. Спорт, семья, самообразование, работа - являются 4 "колесами" нашей жизни
 *
 * */
public class Categories extends Fragment {
    View view;
    ImageView v1;
    ImageView v2;
    ImageView v3;
    ImageView v4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        v1 = view.findViewById(R.id.imageFamile);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Famile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        v2 = view.findViewById(R.id.imageSport);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Health();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        v3 = view.findViewById(R.id.imageStudy);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Study();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        v4 = view.findViewById(R.id.imageBusiness);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Business();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        return view;
    }

}
