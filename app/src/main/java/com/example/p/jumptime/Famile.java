package com.example.p.jumptime;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Famile extends Fragment {
    List<NewsForRecycler> news = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_famile, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        DataAdapterTest adapter = new DataAdapterTest(getContext(), news);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return view;

    }

    private void setInitialData(){

        news.add(new NewsForRecycler ("Какая-то новость",  R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("Elite z3",  R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("Galaxy S8", R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("LG G 5",  R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("Elite z3", R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("Elite z3",  R.mipmap.ic_launcher,getActivity()));
        news.add(new NewsForRecycler ("Elite z3",  R.mipmap.ic_launcher,getActivity()));
    }
}