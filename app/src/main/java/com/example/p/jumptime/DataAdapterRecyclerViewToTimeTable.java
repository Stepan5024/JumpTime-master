package com.example.p.jumptime;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class DataAdapterRecyclerViewToTimeTable extends RecyclerView.Adapter<DataAdapterRecyclerViewToTimeTable.ViewHolder> {
    private LayoutInflater inflater;
    public static ArrayList<TaskForSchedule> news;
    View view;

    static class ViewHolder extends RecyclerView.ViewHolder {
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

    DataAdapterRecyclerViewToTimeTable(Context context, ArrayList<TaskForSchedule> phones) {
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
      final TaskForSchedule new1 =  news.get(position);
      viewHolder.imageView.setImageResource(new1.getImage());
      viewHolder.newsView.setText(new1.getName());
      viewHolder.ViewData.setText(new1.gettaskLong());
      viewHolder.ViewTime.setText(new1.getTaskTime());
        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(new1.getActivity(), "position " + position, Toast.LENGTH_SHORT).show();
                if(new1.getTaskName().compareTo("Добавьте дело")==0 && new1.getTaskTime().compareTo("в понедельник")== 0){
                //    Toast.makeText(new1.getActivity(), "так точно", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(new1.getActivity());
                    final View uview = View.inflate(new1.getActivity(), R.layout.dialog_dela, null);
                    builder.setView(uview);
                    final AlertDialog show = builder.show();

                    ImageView ok = uview.findViewById(R.id.add_item_in_shedule);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            news.clear();
                            news.add(new TaskForSchedule("qwert","120 min", "23;6", R.drawable.plus,new1.getActivity()));
                           // news.add(new TaskForRecyclerView("Добавьте дело", "укажите время", "в понедельник", R.drawable.plus, 0, new1.getActivity()));
                            Monday.tasks.add(news.get(0));
                            Monday.updateUI();
                            show.dismiss();
                        }
                    });
                }




            }
        });
    }

    public void restoreItem(TaskForSchedule task, int position) {
        news.add(position, task);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public List<TaskForSchedule> getData() {
        return news;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

}

