package com.example2.diablove.yakamozrehberi.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example2.diablove.yakamozrehberi.Fragments.CompaniesGalleryFragment;
import com.example2.diablove.yakamozrehberi.Fragments.CompaniesHaritaFragment;

/**
 * Created by Diablove on 7/29/2016.
 */
public class ContactTabAdapter extends FragmentPagerAdapter {
    private String [] cTabTitles;


    public ContactTabAdapter(FragmentManager fm, String [] cTabTitles) {
        super(fm);
        this.cTabTitles = cTabTitles;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CompaniesGalleryFragment();
            case 1:
                return new CompaniesHaritaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.cTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.cTabTitles[position];
    }






}
