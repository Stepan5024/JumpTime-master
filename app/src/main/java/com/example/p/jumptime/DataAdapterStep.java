package com.example.p.jumptime;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class DataAdapterStep extends RecyclerView.Adapter<DataAdapterStep.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<TaskForStep> news;
    View view;

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView ViewName;
        final TextView ViewTime;
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ViewName = view.findViewById(R.id.DataTask);
            ViewTime = view.findViewById(R.id.TimeTask);
            linearLayout = view.findViewById((R.id.linLayout));
        }
    }

    DataAdapterStep(Context context, ArrayList<TaskForStep> phones) {
        this.news = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdapterStep.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycler_view_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapterStep.ViewHolder viewHolder, final int position) {
        final TaskForStep new1 = news.get(position);
        viewHolder.ViewTime.setText(new1.getTaskData());
        viewHolder.ViewName.setText(new1.getTaskName());


        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void restoreItem(TaskForStep task, int position) {
        news.add(position, task);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public List<TaskForStep> getData() {
        return news;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

}

