package com.example.p.jumptime;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Monday extends Fragment {

    public static ArrayList<TaskForSchedule> tasks = new ArrayList();
    static RecyclerView recyclerViewMonday;
    public static DataAdapter adapter;
    static View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monday, container, false);
        recyclerViewMonday = view.findViewById(R.id.list_monday);


        tasks.add(0, new TaskForSchedule("Зарядка", "7:10", "7:00", R.drawable.zaraydka, getActivity()));
        tasks.add(1, new TaskForSchedule("Школа", "15:15", "7:50", R.drawable.school, getActivity()));
        tasks.add(2, new TaskForSchedule("IT школа Samsung", "15:30", "17:00", R.drawable.ic_samsung, getActivity()));
        tasks.add(3, new TaskForSchedule("Домашняя работа", "16:00", "15:30", R.drawable.dz, getActivity()));
        tasks.add(4, new TaskForSchedule("ФМШ МАИ", "19:30", "17:00", R.drawable.phisics, getActivity()));
        tasks.add(5, new TaskForSchedule("Дорога", "20:00", "19:40", R.drawable.avto, getActivity()));
        tasks.add(6, new TaskForSchedule("Сон", "7:00", "22:30", R.drawable.sleep, getActivity()));

        //tasks.add(new TaskForSchedule("test", "test", "test", R.drawable.plus_big, getActivity()));


        adapter = new DataAdapter(getContext(), tasks);

        recyclerViewMonday.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMonday.setLayoutManager(layoutManager);


        return view;

    }

    public static void updateUI() {

        DataAdapterRecyclerViewToTimeTable adapter = new DataAdapterRecyclerViewToTimeTable(view.getContext(), tasks);
        recyclerViewMonday.setAdapter(adapter);

    }

}

class DataAdapter extends RecyclerView.Adapter<DataAdapterRecyclerViewToTimeTable.ViewHolder> {
    private LayoutInflater inflater;
    public static ArrayList<TaskForSchedule> news;
    View view;
    AlertDialog.Builder builder;
    AlertDialog show;

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

    DataAdapter(Context context, ArrayList<TaskForSchedule> phones) {
        this.news = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdapterRecyclerViewToTimeTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycler_view_to_timetable, parent, false);
        builder = new AlertDialog.Builder(view.getContext());
        return new DataAdapterRecyclerViewToTimeTable.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final DataAdapterRecyclerViewToTimeTable.ViewHolder viewHolder, final int position) {
        final TaskForSchedule new1 = news.get(position);
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
                if (new1.getTaskName().compareTo("Добавьте дело") == 0 && new1.getTaskTime().compareTo("в понедельник") == 0) {


                    final View uview = View.inflate(new1.getActivity(), R.layout.dialog_dela, null);
                    builder.setView(uview);
                    show = builder.show();
                    final TextView tv = uview.findViewById(R.id.textView17);
                    tv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {


                        }
                    });
                    Button timeButton = (Button) uview.findViewById(R.id.button1);
                    final TimePicker mTimePicker1 = (TimePicker) uview.findViewById(R.id.picker1);
                    Calendar now = Calendar.getInstance();
                    mTimePicker1.setIs24HourView(true);
                    mTimePicker1.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
                    mTimePicker1.setCurrentMinute(now.get(Calendar.MINUTE));

                    mTimePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

                        @Override
                        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {



                        }
                    });

                    final TimePicker mTimePicker = (TimePicker) uview.findViewById(R.id.picker2);
                    mTimePicker.setIs24HourView(true);
                    timeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv.setText(new StringBuilder()
                                    .append(mTimePicker1.getCurrentHour()).append(":")
                                    .append(mTimePicker1.getCurrentMinute()).append("--")
                                    .append(mTimePicker.getCurrentHour()).append(":")
                                    .append(mTimePicker.getCurrentMinute()));

                        }
                    });
                    mTimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
                    mTimePicker.setCurrentMinute(now.get(Calendar.MINUTE));

                    mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

                        @Override
                        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                            Toast.makeText(view.getContext(), "onTimeChanged",
                                    Toast.LENGTH_SHORT).show();


                            tv.setText("Часы: " + hourOfDay + "\n" + "Минуты: "
                                    + minute);
                        }
                    });

                    ImageView ok = uview.findViewById(R.id.add_item_in_shedule);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // news.add(new TaskForSchedule("qwert","120 min", "23;6", R.drawable.plus,new1.getActivity()));
                          Monday.tasks.remove(0);
                          Monday.tasks.add(0, new TaskForSchedule("Добавьте дело", "укажите время", "в понедельник", R.drawable.plus, new1.getActivity()));
                            // news.add(new TaskForRecyclerView("Добавьте дело", "укажите время", "в понедельник", R.drawable.plus, 0, new1.getActivity()));
                            Monday.tasks.add(new TaskForSchedule("qwert", "120 min", "23;6", R.drawable.plus, new1.getActivity()));
                            Monday.updateUI();
                            show.dismiss();
                        }
                    });
                }


            }
        });
    }

    /*  private void setInitialDateTime() {

          tvTime.setText(DateUtils.formatDateTime(view.getContext(),
                  dateAndTime.getTimeInMillis(),
                  DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
      }*/
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


