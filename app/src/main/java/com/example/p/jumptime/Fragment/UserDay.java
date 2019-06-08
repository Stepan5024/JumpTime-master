package com.example.p.jumptime.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.p.jumptime.Fragment.UserAnswers;
import com.example.p.jumptime.R;

/*
 *
 * По наатию на краткий ответ открывается фрагмент UserAnswers  с переходным пораметром - Good, normal, bad
 * */
public class UserDay extends Fragment {

    View view;


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_day, container, false);

        ImageButton v1 = view.findViewById(R.id.perfectDay);
        ImageButton v2 = view.findViewById(R.id.normalDay);
        ImageButton v3 = view.findViewById(R.id.badDay);


        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UserAnswers();
                Bundle bundle = new Bundle();
                bundle.putString("key", "Good");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UserAnswers();
                Bundle bundle = new Bundle();
                bundle.putString("key", "Normal");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UserAnswers();
                Bundle bundle = new Bundle();
                bundle.putString("key", "Bad");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        return view;

    }
}
