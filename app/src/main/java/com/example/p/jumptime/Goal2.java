package com.example.p.jumptime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;


public class Goal2 extends Fragment {
    View view;
    private SlidingUpPanelLayout slidingLayout;
    private Button btnShow;
    private Button btnHide;
    private TextView textView;
    SlidingPaneLayout slidingPaneLayout;
    private Button newGoal;
    ViewFlipper flipper;
    float fromPosition;
    public View onCreateView(LayoutInflater inflater,
                @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.test_pane, container, false);
        btnShow = (Button)view.findViewById(R.id.btn_show);
        btnHide = (Button)view.findViewById(R.id.btn_hide);
        textView = (TextView)view.findViewById(R.id.text);
        newGoal = view.findViewById(R.id.butn_new_goal);

        newGoal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {


                Fragment fragment = new AddGoal();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            }
        });

        //set layout slide listener
        slidingLayout = (SlidingUpPanelLayout)view.findViewById(R.id.sliding_layout);

        //some "demo" event
        slidingLayout.setPanelSlideListener(onSlideListener());
        btnHide.setOnClickListener(onHideListener());
        btnShow.setOnClickListener(onShowListener());
        return view;
    }
    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                // fromPosition - координата по оси X начала выполнения операции
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                float toPosition = event.getX();
                if (fromPosition > toPosition)
                    flipper.showNext();
                else if (fromPosition < toPosition)
                    flipper.showPrevious();
            default:
                break;
        }
        return true;
    }
    /**
     * Request show sliding layout when clicked
     * @return
     */
    private View.OnClickListener onShowListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show sliding layout in bottom of screen (not expand it)
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                btnShow.setVisibility(View.GONE);
            }
        };
    }

    /**
     * Hide sliding layout when click button
     * @return
     */
    private View.OnClickListener onHideListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide sliding layout
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                btnShow.setVisibility(View.VISIBLE);
            }
        };
    }

    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        return new SlidingUpPanelLayout.PanelSlideListener() {

            public void onPanelSlide(View view, float v) {
                textView.setText("panel is sliding");
            }

            public void onPanelCollapsed(View view) {
                textView.setText("panel Collapse");
            }

            public void onPanelExpanded(View view) {
                textView.setText("panel expand");
            }


            public void onPanelAnchored(View view) {
                textView.setText("panel anchored");
            }


            public void onPanelHidden(View view) {
                textView.setText("panel is Hidden");
            }
        };
    }
}

