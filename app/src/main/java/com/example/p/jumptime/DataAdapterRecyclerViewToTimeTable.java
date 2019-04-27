package com.example.p.jumptime;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class DataAdapterRecyclerViewToTimeTable extends RecyclerView.Adapter<DataAdapterRecyclerViewToTimeTable.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<TaskForRecyclerView> news;
    View view;

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView newsView;
        final TextView ViewData;
        final TextView ViewTime;
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iconImage);
            newsView = view.findViewById(R.id.TaskName);
            ViewData = view.findViewById(R.id.TaskLong);
            ViewTime = view.findViewById(R.id.DataStart);
            linearLayout = view.findViewById((R.id.linLayout));
        }
    }

    DataAdapterRecyclerViewToTimeTable(Context context, ArrayList<TaskForRecyclerView> phones) {
        this.news = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdapterRecyclerViewToTimeTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycler_view_to_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapterRecyclerViewToTimeTable.ViewHolder viewHolder, final int position) {
        final TaskForRecyclerView new1 = news.get(position);
        if(new1.getName().compareTo("Добавьте дело")==0)viewHolder.imageView.setImageResource(R.mipmap.default_ic);
        viewHolder.imageView.setImageResource(new1.getImage());
        viewHolder.newsView.setText(new1.getName());
        viewHolder.ViewData.setText(new1.getTaskData());
        viewHolder.ViewTime.setText(new1.getTaskTime());

        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

               /* Fragment fragment = new TasksForCurrentPerfomance();
                FragmentTransaction fragmentManager = new1.getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container, fragment).commit();*/

            }
        });
    }

    public void restoreItem(TaskForRecyclerView task, int position) {
        news.add(position, task);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public List<TaskForRecyclerView> getData() {
        return news;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

}

