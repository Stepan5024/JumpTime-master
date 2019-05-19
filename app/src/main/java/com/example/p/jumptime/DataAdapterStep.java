package com.example.p.jumptime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class DataAdapterStep extends RecyclerView.Adapter<DataAdapterStep.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<TaskForStep> news;
    View view;
    String title = "Анализ";
    String message = "Выбери нужное действие";
    String button1String = "Редактировать";
    String button2String = "Выполнено!";
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

                AlertDialog.Builder ad = new AlertDialog.Builder(new1.getActivity());
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //происходит редактирование
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(new1.getActivity());
                        final View uview = View.inflate(new1.getActivity(), R.layout.dialog_new_plan_task, null);
                        builder.setView(uview);
                        final android.support.v7.app.AlertDialog show = builder.show();

                        Button ok = uview.findViewById(R.id.button6);
                        final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                       // ed_in_alertDialog.setText(Step.tasks.get(position));
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                             /*   // происходит удаление из бд
                                Toast.makeText(getContext(), "index = " + i, Toast.LENGTH_SHORT).show();
                                DeleteIndexFromSQLite((Integer) indexMonth.get(i));
                                arMonth.remove(i);
                                indexMonth.remove(i);
                                saveInDataBase("month", ed_in_alertDialog.getText().toString());

                                arMonth.add(ed_in_alertDialog.getText().toString());

                                updateUI();
                                show.dismiss();*/
                            }
                        });
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        // происходит удал/* DeleteIndexFromSQLite((Integer) indexMonth.get(i));
                        //                        arMonth.remove(i);
                        //                        updateUI();*/ение из бд

                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        //пользователь ничего не выбрал
                    }
                });
                ad.show();
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

