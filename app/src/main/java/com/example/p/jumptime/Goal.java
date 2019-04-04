package com.example.p.jumptime;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Goal extends Fragment {
    View  view;

    SlidingPaneLayout slidingPaneLayout;


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pane_testing, container, false);
        slidingPaneLayout = (SlidingPaneLayout) view.findViewById(R.id.slidingpanelayout);

        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {

            @Override
            public void onPanelClosed(View panel) {

                switch (panel.getId()) {
                    case R.id.fragment_secondpane:
                        getActivity().getFragmentManager().findFragmentById(R.id.fragment_firstpane).setHasOptionsMenu(false);
                        getActivity().getFragmentManager().findFragmentById(R.id.fragment_secondpane).setHasOptionsMenu(true);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPanelOpened(View panel) {
                switch (panel.getId()) {
                    case R.id.fragment_secondpane:
                        getActivity().getFragmentManager().findFragmentById(R.id.fragment_firstpane).setHasOptionsMenu(true);
                        getActivity().getFragmentManager().findFragmentById(R.id.fragment_secondpane).setHasOptionsMenu(false);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

        });
        return view;
    }

}