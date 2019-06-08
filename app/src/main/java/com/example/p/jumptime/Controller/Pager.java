package com.example.p.jumptime.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.p.jumptime.Fragment.KILO;
import com.example.p.jumptime.Fragment.LineFragment;
import com.example.p.jumptime.Fragment.Step;

public class Pager extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[]{"КИЛО", "ШАГИ", "ГРАФИК"};

    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //оличество вкладок
        this.tabCount = tabCount;
    }


    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                KILO tab1 = new KILO();
                return tab1;
            case 1:
                Step tab2 = new Step();
                return tab2;
            case 2:
                LineFragment tab3 = new LineFragment();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabCount;
    }


}