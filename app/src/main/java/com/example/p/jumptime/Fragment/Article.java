package com.example.p.jumptime.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p.jumptime.R;

/*
* Фрагмент открывается при нажатии в меню на "Тфйм-менеджмент"\
* Отображает статью "Как добиться свойе цели"
*
* */
public class Article extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);

        return view;

    }


}