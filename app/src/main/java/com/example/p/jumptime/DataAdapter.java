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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TaskForRecyclerView> news;


    DataAdapter(Context context, List<TaskForRecyclerView> phones) {
        this.news = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder viewHolder, final int position) {
        final TaskForRecyclerView new1 = news.get(position);
        viewHolder.imageView.setImageResource(new1.getImage());
        viewHolder.newsView.setText(new1.getName());
        viewHolder.ViewData.setText(new1.getTaskData());
        viewHolder.ViewTime.setText(new1.getTaskTime());

        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Fragment fragment = new Tab2();
                FragmentTransaction fragmentManager = new1.getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container, fragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView newsView;
        final TextView ViewData;
        final TextView ViewTime;
        LinearLayout linearLayout;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            newsView = (TextView) view.findViewById(R.id.TaskName);
            ViewData = (TextView) view.findViewById(R.id.DataTask);
            ViewTime = (TextView) view.findViewById(R.id.TimeTask);
            linearLayout = (LinearLayout) view.findViewById((R.id.linLayout));
        }
    }
}

