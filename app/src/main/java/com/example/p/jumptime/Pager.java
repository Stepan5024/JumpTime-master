package com.example.p.jumptime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[]{"КИЛО", "ШАГИ", "ГРАФИК"};

    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
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
                Graph tab3 = new Graph();
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