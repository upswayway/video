package com.example.hdvideoplayer;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.hdvideoplayer.fragment.AudioFragment.modelList;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hdvideoplayer.databinding.ActivityAudioplayBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Formatter;
import java.util.Locale;

public class AudioPlayActivity extends AppCompatActivity {

    private ExoPlayer exoPlayer;

    private boolean isPlaying = false;
    private Handler handler;
    int mCurrentPosition;
    private int position;
    private String endtime;
    private int seekForwardTime = 5000;
    private int seekPriviewTime = -5000;
    private int nextAudi = 1;
    private int nextAudii = 1;


    Player.Listener eventListener = new Player.Listener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);

            switch (playbackState) {
                case ExoPlayer.STATE_ENDED:

                    setPlayPause(false);
                    exoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:

                    setProgress();
                    break;
                case ExoPlayer.STATE_BUFFERING:

                    break;
                case ExoPlayer.STATE_IDLE:

                    break;
            }
        }
    };

    ActivityAudioplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAudioplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        position = getIntent().getExtras().getInt("play", 0);
        binding.audioname.setText(modelList.get(position).getaName());
        prepareExoPlayerFromFileUri(Uri.parse(modelList.get(position).getaPath()));
        binding.audioname.setSelected(true);


        binding.nextaudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (modelList.size() > 0) {
                    exoPlayer.stop();
                    prepareExoPlayerFromFileUri(Uri.parse(modelList.get(position + nextAudi).getaPath()));
                    binding.audioname.setText(modelList.get(position + nextAudi).getaName());
                    setPlayPause(true);
                    nextAudi++;
                }
            }
        });

        binding.previewaudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelList.size() > 0) {
                    exoPlayer.stop();
                    prepareExoPlayerFromFileUri(Uri.parse(modelList.get(position + nextAudii).getaPath()));
                    binding.audioname.setText(modelList.get(position + nextAudi).getaName());
                    setPlayPause(true);
                    nextAudii--;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPlayPause(!isPlaying);
        binding.btnPlay.setImageResource(R.drawable.pause_btn);
        Log.e("===+", "onResume: " + modelList.get(position).getaPath());

    }

    private void prepareExoPlayerFromFileUri(Uri uri) {
        {
            exoPlayer = new ExoPlayer.Builder(this).build();
            exoPlayer.addListener(eventListener);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Hd Video Player"));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            initMediaControls();
        }
    }

    private void initMediaControls() {
        initPlayButton();
        initSeekBar();
        initforwerdaudioButton();
        initpreviewaudioButton();
    }

    private void initpreviewaudioButton() {

        binding.previewaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priviewsong();
            }
        });
    }

    private void initforwerdaudioButton() {
        binding.nextaudio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.e("===", "run: " + mCurrentPosition);
                Log.e(TAG, "run== " + endtime);
                forwardSong();
            }
        });
    }

    public void forwardSong() {
        if (exoPlayer != null) {
            int currentPosition = (int) exoPlayer.getCurrentPosition();
            if (currentPosition + seekForwardTime <= exoPlayer.getDuration()) {
                exoPlayer.seekTo(currentPosition + seekForwardTime);
            } else {
                exoPlayer.seekTo(exoPlayer.getDuration());
            }
        }
    }

    public void priviewsong() {
        if (exoPlayer != null) {
            int currentPosition = (int) exoPlayer.getCurrentPosition();
            if (currentPosition + seekPriviewTime <= exoPlayer.getDuration()) {
                exoPlayer.seekTo(currentPosition + seekPriviewTime);
            } else {
                exoPlayer.seekTo(exoPlayer.getDuration());
            }
        }
    }

    private void initPlayButton() {

        binding.btnPlay.requestFocus();
        binding.btnPlay.setOnClickListener(view -> setPlayPause(!isPlaying));
    }

    private void setPlayPause(boolean play) {
        isPlaying = play;
        exoPlayer.setPlayWhenReady(play);
        if (!isPlaying) {
            binding.btnPlay.setImageResource(R.drawable.playm_btn);
        } else {
            setProgress();
            binding.btnPlay.setImageResource(R.drawable.pause_btn);
        }
    }

    private void setProgress() {
        binding.mediacontrollerProgress.setProgress(0);
        binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
        binding.timeCurrent.setText("" + stringForTime((int) exoPlayer.getCurrentPosition()));
        binding.playerEndTime.setText("" + stringForTime((int) exoPlayer.getDuration()));

        if (handler == null) handler = new Handler();
        //Make sure you update Seekbar on UI thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && isPlaying) {
                    binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
                    mCurrentPosition = (int) exoPlayer.getCurrentPosition() / 1000;
                    binding.mediacontrollerProgress.setProgress(mCurrentPosition);

                    binding.timeCurrent.setText(stringForTime((int) exoPlayer.getCurrentPosition()));
                    Log.e(TAG, "run: " + stringForTime((int) exoPlayer.getCurrentPosition()));
                    binding.playerEndTime.setText(stringForTime((int) exoPlayer.getDuration()));
                    endtime = stringForTime((int) exoPlayer.getDuration());
                    handler.postDelayed(this, 1000);
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

    private void initSeekBar() {

        binding.mediacontrollerProgress.requestFocus();

        binding.mediacontrollerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                exoPlayer.seekTo(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.mediacontrollerProgress.setMax(0);
        binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.stop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}