package com.example.p.jumptime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class History extends Fragment {
    RecyclerView recyclerView;
    ArrayList cards;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.list);
        DataAdapterHistory adapter = new DataAdapterHistory(getContext(), cards);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return view;

    }


}