package com.example.p.jumptime;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
public class Pager extends Fragment {
    private String tabTitles[] = new String[] { "КАЛЕНДАРЬ", "СПИСОК ДЕЛ"};
    //integer to count number of tabs
    int tabCount;



    //Overriding method getItem

    public Fragment getItem( int  position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;

            default:
                return null;
        }
    }
     public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
    //Overriden method getCount to get the number of tabsd

    public int getCount() {
        return tabCount;
    }


}