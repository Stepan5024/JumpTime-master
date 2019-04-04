package com.example.p.jumptime;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.View;

public class Pane_testing extends Activity {

    SlidingPaneLayout slidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pane_testing);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanelayout);

        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {

            @Override
            public void onPanelClosed(View panel) {

                switch (panel.getId()) {
                    case R.id.fragment_secondpane:
                        getFragmentManager().findFragmentById(R.id.fragment_firstpane).setHasOptionsMenu(false);
                        getFragmentManager().findFragmentById(R.id.fragment_secondpane).setHasOptionsMenu(true);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPanelOpened(View panel) {
                switch (panel.getId()) {
                    case R.id.fragment_secondpane:
                        getFragmentManager().findFragmentById(R.id.fragment_firstpane).setHasOptionsMenu(true);
                        getFragmentManager().findFragmentById(R.id.fragment_secondpane).setHasOptionsMenu(false);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

        });
    }


}
