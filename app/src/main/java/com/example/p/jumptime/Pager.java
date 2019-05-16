package com.example.p.jumptime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[] { "КИЛО", "УСПЕХИ", "ГРАФИК","4 КОЛЕСА"};
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem

    public Fragment getItem( int  position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                KILO tab1 = new KILO();
                return tab1;
            case 1:
                Tab1 tab2 = new Tab1();
                return tab2;
            case 2:
                Tab1 tab3 = new Tab1();
                return tab3;
            case 3:
                Tab1 tab4 = new Tab1();
                return tab4;
            default:
                return null;
        }
    }
    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
    //Overriden method getCount to get the number of tabsd
    @Override
    public int getCount() {
        return tabCount;
    }


}