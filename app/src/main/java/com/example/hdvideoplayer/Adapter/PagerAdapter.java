package com.example.hdvideoplayer.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.hdvideoplayer.fragment.AudioFragment;
import com.example.hdvideoplayer.fragment.ImageFragment;
import com.example.hdvideoplayer.fragment.VideoFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = new Fragment();
        switch (pos) {
            case 0:
                return new AudioFragment();
            case 1:
                return new ImageFragment();
            case 2:
                return new VideoFragment();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
