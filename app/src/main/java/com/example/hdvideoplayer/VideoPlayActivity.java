package com.example.hdvideoplayer;

import static com.example.hdvideoplayer.fragment.VideoFragment.demolist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hdvideoplayer.databinding.ActivityVideoplayBinding;

import java.util.Formatter;
import java.util.Locale;

public class VideoPlayActivity extends AppCompatActivity {

    private int position;
    private int seekForwardTime = 9000;
    private int seekPriviewTime = -5000;

    ActivityVideoplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        position = getIntent().getExtras().getInt("plays", 0);
        binding.videoview.setVideoPath(demolist.get(position).getPath());
        binding.videoview.start();
        initbtnPlayvideo();
        setProgressbar();
        initpreviewvideo();
        initforwerdvideo();


        binding.nextvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (demolist.size() > 0 && position < demolist.size()) {
                    binding.videoview.pause();
                    binding.videoview.setVideoPath(demolist.get(position + 1).getPath());
                    binding.videoview.start();
                    position++;
                }
            }
        });
        binding.prevvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (demolist.size() > 0 && position > 0) {
                    binding.videoview.pause();
                    binding.videoview.setVideoPath(demolist.get(position - 1).getPath());
                    binding.videoview.start();
                    position--;
                }
            }
        });
    }

    private void initpreviewvideo() {

        binding.previewvideo.setOnClickListener(view -> {
            if (binding.videoview != null) {
                int currentPosition = (int) binding.videoview.getCurrentPosition();
                if (currentPosition + seekPriviewTime <= binding.videoview.getDuration()) {
                    binding.videoview.seekTo(currentPosition + seekPriviewTime);
                } else {
                    binding.videoview.seekTo(binding.videoview.getDuration());
                }
            }
        });
    }

    private void initforwerdvideo() {
        binding.forwerdvideo.setOnClickListener(view -> {
            if (binding.videoview != null) {
                int currentPosition = (int) binding.videoview.getCurrentPosition();
                if (currentPosition <= binding.videoview.getDuration()) {
                    binding.videoview.seekTo(currentPosition + seekForwardTime);
                    Log.e("TAG", "onClick: " + currentPosition);
                } else {
                    binding.videoview.seekTo(binding.videoview.getDuration());
                    Log.e("===", "onClick: " + binding.videoview.getDuration());
                }
            }
        });
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private void setProgressbar() {
        binding.videoview.setOnPreparedListener(mp -> {
            binding.mediacontrollerProgressvideo.setMax(binding.videoview.getDuration());
            binding.mediacontrollerProgressvideo.postDelayed(onEverySecond, 1000);
        });


        binding.mediacontrollerProgressvideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    // this is when actually seekbar has been seeked to a new position
                    binding.videoview.seekTo(progress);
                }
                int songCurrentTime = binding.videoview.getCurrentPosition();
                int songTotalTime = binding.videoview.getDuration();

                binding.timeCurrentvideo.setText("" + stringForTime(songCurrentTime));
                binding.playerEndTimevideo.setText("" + stringForTime(songTotalTime));
            }
        });

    }

    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {

            if (binding.mediacontrollerProgressvideo != null) {
                binding.mediacontrollerProgressvideo.setProgress(binding.videoview.getCurrentPosition());
            }
            if (binding.videoview.isPlaying()) {
                binding.mediacontrollerProgressvideo.postDelayed(onEverySecond, 1000);
            }
        }
    };

    private void initbtnPlayvideo() {
        binding.btnPlayvideo.setOnClickListener(view -> {
            if (!binding.videoview.isPlaying()) {
                binding.videoview.start();
                binding.btnPlayvideo.setImageResource(R.drawable.pause_btn);
            } else {
                binding.videoview.pause();
                binding.btnPlayvideo.setImageResource(R.drawable.playm_btn);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}