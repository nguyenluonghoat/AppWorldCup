package com.example.btvnday12.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btvnday12.fragment.MatchFragment;
import com.example.btvnday12.fragment.PlayerFragment;

public class FootBallPagerAdapter extends FragmentPagerAdapter {
    private String[] title = new String[]{"Lịch đấu", "Cầu thủ"};

    public FootBallPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MatchFragment.getInstance();
            case 1:
                return PlayerFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
