package com.example.hdvideoplayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hdvideoplayer.Adapter.PagerAdapter;
import com.example.hdvideoplayer.databinding.ActivityFunctionBinding;

public class FunctionActivity extends AppCompatActivity {

    private int click;

    ActivityFunctionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFunctionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewPager();
        setBottomBar();
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setOffscreenPageLimit(0);
        click = getIntent().getIntExtra("click", 0);
        binding.viewPager.setCurrentItem(click);


    }

    private void setBottomBar() {
        binding.bottomNavigationView.setItemIconTintList(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.id_audio:
                    binding.viewPager.setCurrentItem(0);
                    break;
                case R.id.id_image:
                    binding.viewPager.setCurrentItem(1);
                    break;
                case R.id.id_video:
                    binding.viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    private void setViewPager() {
        binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setBottomSelect(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setBottomSelect(int position) {
        switch (position) {
            case 0:
                binding.viewPager.setCurrentItem(0);
                binding.titelname.setText("Audio player");
                binding.bottomNavigationView.setSelectedItemId(R.id.id_audio);
                break;
            case 1:
                binding.viewPager.setCurrentItem(1);
                binding.titelname.setText("Image view");
                binding.bottomNavigationView.setSelectedItemId(R.id.id_image);
                break;
            case 2:
                binding.viewPager.setCurrentItem(2);
                binding.titelname.setText("Video player");
                binding.bottomNavigationView.setSelectedItemId(R.id.id_video);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }
}