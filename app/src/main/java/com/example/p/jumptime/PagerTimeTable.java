package com.example.p.jumptime;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerTimeTable extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[] { "ПН", "ВТ", "СР","ЧТ","ПТ","СБ","ВС"};
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerTimeTable(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem

    public Fragment getItem( int  position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Monday tab1 = new Monday();
                return tab1;
            case 1:
               Tuesday tab2 = new Tuesday();
                return tab2;
            case 2:
               Wendnesday tab3 = new Wendnesday();
                return tab3;
            case 3:
                Thurday tab4 = new Thurday();
                return tab4;
            case 4:
                Friday tab5 = new Friday();
                return tab5;
            case 5:
                Sunday tab6 = new Sunday();
                return tab6;
            case 6:
                Thurday tab7 = new Thurday();
                return tab7;
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