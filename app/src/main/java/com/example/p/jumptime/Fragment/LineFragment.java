package com.example.p.jumptime.Fragment;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.p.jumptime.R;

import im.dacer.androidcharts.LineView;
import java.util.ArrayList;

/**
 * данный фрагмент используется в TabLayout вкладке "График"
 * реализует построение графика по количеству выполненных дел на неделе
 */
public class LineFragment extends Fragment {
    int randomint = 7;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line, container, false);
        final LineView lineView = (LineView) rootView.findViewById(R.id.line_view);


        initLineView(lineView);

        randomSet(lineView);
        return rootView;
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    // Уважаемый эксперт поставлена заглушка показывающая рамдомную динамику
    // сейчас работаю над логикой показа динамики выполненных дел за неделю
    private void randomSet(LineView lineView) {
        ArrayList<Integer> dataList = new ArrayList<>();
        float random = (float) (Math.random() *20 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList.add((int) (Math.random() * random));
        }


        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);


        lineView.setDataList(dataLists);

        ArrayList<Float> dataListF = new ArrayList<>();
        float randomF = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF2 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF2.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF3 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF3.add((float) (Math.random() * randomF));
        }

        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
        dataListFs.add(dataListF);
        dataListFs.add(dataListF2);
        dataListFs.add(dataListF3);


    }
}