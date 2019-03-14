package com.example.p.jumptime;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Goal extends Fragment {

    View view;
    public Goal() {
    }



    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        drawer.openDrawer(Gravity.LEFT);
        Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();
            drawer.openDrawer(Gravity.LEFT);
        } else {
           drawer.openDrawer(Gravity.LEFT);
        }
    }
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
               view = inflater.inflate(R.layout.fragment_add_task, container, false);

        return view;
    }
}