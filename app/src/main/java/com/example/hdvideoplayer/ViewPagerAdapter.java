package com.example.hdvideoplayer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.hdvideoplayer.fragment.PreviewFragment;
import java.util.ArrayList;
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ImageViewActivity imageViewActivity;
    ArrayList<String> imageData;

    public ViewPagerAdapter(FragmentManager fragmentManager,ImageViewActivity imageViewActivity, ArrayList<String> imageData) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.imageViewActivity = imageViewActivity;
        this.imageData = imageData;
    }

    @Override
    public int getCount() {
        return imageData.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PreviewFragment.newInstance(imageData.get(position));
    }
}
