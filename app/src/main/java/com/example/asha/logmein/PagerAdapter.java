package com.example.asha.logmein;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.security.acl.NotOwnerException;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfFragments;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int NoOfFragments)
    {
        super(fm);
        this.mNoOfFragments = NoOfFragments;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: LoginFragment FL = new LoginFragment();
                    return FL;
            case 1: SignUpFragment FS = new SignUpFragment();
                    return FS;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfFragments;
    }
}
