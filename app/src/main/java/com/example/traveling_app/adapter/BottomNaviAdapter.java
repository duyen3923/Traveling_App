package com.example.traveling_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.traveling_app.fragment.Menu_Home;
import com.example.traveling_app.fragment.Menu_Love;
import com.example.traveling_app.fragment.Menu_Menu;

public class BottomNaviAdapter extends FragmentStatePagerAdapter {
    public BottomNaviAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Menu_Home();
            case 1:
                return new Menu_Love();
            case 2:
                return new Menu_Menu();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
