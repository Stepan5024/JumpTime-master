package com.example.p.jumptime.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p.jumptime.Model.FastItemForRecycler;
import com.example.p.jumptime.R;

import java.util.ArrayList;
import java.util.List;

/*В этом фрагменте будет происходить выбор 4 персонажей-меток,
 *если пользователь каждый день будет увеличивать количество выполненных дел на протяжении недели - его ждет бонус новый персонаж,
 *в этом франгменте он может поменять 4 обычных на 1 суперкрутого боса
 **/
public class Statistic extends Fragment {

    List<FastItemForRecycler> item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        item = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        DataAdapterTest adapter = new DataAdapterTest(getContext(), item);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return view;

    }

    private void setInitialData() {

        item.add(new FastItemForRecycler("А ты ", R.drawable.j1, getActivity()));
        item.add(new FastItemForRecycler("готов ", R.drawable.j2, getActivity()));
        item.add(new FastItemForRecycler("работать ", R.drawable.j3, getActivity()));
        item.add(new FastItemForRecycler("эффективно? ", R.drawable.j5, getActivity()));
        item.add(new FastItemForRecycler("Тогда вперед! ", R.drawable.j4, getActivity()));

    }

    class DataAdapterTest extends RecyclerView.Adapter<DataAdapterTest.ViewHolder> {

        private LayoutInflater inflater;
        private List<FastItemForRecycler> arr;
        public String activeString = "";

        DataAdapterTest(Context context, List<FastItemForRecycler> list) {
            this.arr = list;
            this.inflater = LayoutInflater.from(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycler_viewtest, parent, false);
            return new DataAdapterTest.ViewHolder(view);
        }


        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
            final FastItemForRecycler new1 = arr.get(position);
            viewHolder.imageView.setImageResource(new1.getImage());
            viewHolder.newsView.setText(new1.getName());

            // обработчик нажатия
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    activeString = new1.getName();
                    Famile.ed.setText(activeString);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;
            final TextView newsView;
            LinearLayout linearLayout;

            ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image);
                newsView = (TextView) view.findViewById(R.id.news);
                linearLayout = (LinearLayout) view.findViewById((R.id.linLayout));
            }
        }
    }
}
