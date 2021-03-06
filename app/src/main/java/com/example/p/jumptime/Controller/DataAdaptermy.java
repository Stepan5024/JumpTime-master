package com.example.p.jumptime.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.p.jumptime.R;
import com.example.p.jumptime.Model.TaskForRecyclerView;
import com.example.p.jumptime.Fragment.TasksForCurrentPerfomance;

import java.util.ArrayList;
import java.util.List;

public class DataAdaptermy extends RecyclerView.Adapter<DataAdaptermy.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<TaskForRecyclerView> tasks;
    View view;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView tasksView;
        final TextView ViewData;
        final TextView ViewTime;
        RelativeLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            tasksView = view.findViewById(R.id.TaskName);
            ViewData = view.findViewById(R.id.DataTask);
            ViewTime = view.findViewById(R.id.TimeTask);
            linearLayout = view.findViewById((R.id.linLayout));
        }
    }

    public DataAdaptermy(Context context, ArrayList<TaskForRecyclerView> phones) {
        this.tasks = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdaptermy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final DataAdaptermy.ViewHolder viewHolder, final int position) {
        final TaskForRecyclerView new1 = tasks.get(position);
        viewHolder.imageView.setImageResource(new1.getUrl());
        viewHolder.tasksView.setText(new1.getName());
        viewHolder.ViewData.setText(new1.getTaskData());
        viewHolder.ViewTime.setText(new1.getTaskTime());

        if (TasksForCurrentPerfomance.hashMap.get(new1.getName()) == 0) {
        } else if (TasksForCurrentPerfomance.hashMap.get(new1.getName()) == 1) {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#9FEEFF41"));
        } else if (TasksForCurrentPerfomance.hashMap.get(new1.getName()) == 2) {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#BAFFAB40"));
        } else {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#C8FF5252"));
        }



        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Fragment fragment = new TasksForCurrentPerfomance();
                FragmentTransaction fragmentManager = new1.getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container, fragment).commit();

            }
        });
    }

    public void restoreItem(TaskForRecyclerView task, int position) {
        tasks.add(position, task);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public List<TaskForRecyclerView> getData() {
        return tasks;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}

