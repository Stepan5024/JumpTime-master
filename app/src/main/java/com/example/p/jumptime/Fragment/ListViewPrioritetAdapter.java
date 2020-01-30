package com.example.p.jumptime.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.p.jumptime.R;

import java.util.List;

public class ListViewPrioritetAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<ListViewPrioritetBolvanka> listStorage;

    ViewHolder listViewHolder;

    public ListViewPrioritetAdapter(Context context, List<ListViewPrioritetBolvanka> customizedListView) {
        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }
    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewPrioritetAdapter.ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ListViewPrioritetAdapter.ViewHolder();
            convertView = lInflater.inflate(R.layout.listview_for_aims_prioritet, parent, false);

            listViewHolder.name = (TextView)convertView.findViewById(R.id.name_of_aims);
            listViewHolder.decription = (TextView)convertView.findViewById(R.id.description_of_aims);
            listViewHolder.time = (TextView)convertView.findViewById(R.id.time_of_aims);
            listViewHolder.colorBack =(ImageView)convertView.findViewById(R.id.color_back);
            listViewHolder.colorFront =(ImageView)convertView.findViewById(R.id.color_front);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ListViewPrioritetAdapter.ViewHolder)convertView.getTag();

        }
        listViewHolder.name.setText(listStorage.get(position).getName());
        listViewHolder.decription.setText(listStorage.get(position).getDescription());
        listViewHolder.time.setText(listStorage.get(position).getTime());
        listViewHolder.colorBack.setImageResource(listStorage.get(position).getIdColorBack());
       /// listViewHolder.colorFront.setImageResource(listStorage.get(position).getIdColorFront());

        return convertView;
    }
    static class ViewHolder{

        TextView name;
        TextView decription;
        TextView time;
        ImageView colorBack;
        ImageView colorFront;
    }
}
