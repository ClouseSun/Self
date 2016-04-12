package com.abition.self;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by KlousesSun on 16/4/10.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PlanFragment.getInstance();
            case 1:
                return ShareFragment.getInstance();
            case 2:
                return MeFragment.getInstance();
            default:
                return null;
        }
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
