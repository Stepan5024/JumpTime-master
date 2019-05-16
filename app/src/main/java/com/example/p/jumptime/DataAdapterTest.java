package com.example.p.jumptime;
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

import java.util.List;

class DataAdapterTest extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<NewsForRecycler> news;


    DataAdapterTest(Context context, List<NewsForRecycler> phones) {
        this.news = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_viewtest, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder viewHolder, final int position) {
        final NewsForRecycler new1 = news.get(position);
        viewHolder.imageView.setImageResource(new1.getImage());
        viewHolder.newsView.setText(new1.getName());

        // обработчик нажатия
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

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
        LinearLayout linearLayout;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            newsView = (TextView) view.findViewById(R.id.news);
            linearLayout = (LinearLayout) view.findViewById((R.id.linLayout));
        }
    }
}

