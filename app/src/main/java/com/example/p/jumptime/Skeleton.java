package com.example.p.jumptime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Skeleton extends Fragment {
    View view;
    String i;
    ImageView im;
    ArrayList arrlist = new ArrayList();
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shedule, container, false);
        im = view.findViewById(R.id.add_item_in_shedule);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Monday();
                arrlist.add("name");
                arrlist.add("data");
                arrlist.add("time");
                arrlist.add("1");
                arrlist.add("23");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("arraylist", arrlist);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             i = bundle.getString("key");
        }
        TextView text = view.findViewById(R.id.textView11);
        text.setText(i);
        return view;

    }
}
