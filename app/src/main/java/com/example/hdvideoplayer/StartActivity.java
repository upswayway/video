package com.example.hdvideoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hdvideoplayer.databinding.ActivitySecundBinding;
import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.intuit.sdp.BuildConfig;

public class StartActivity extends AppCompatActivity {

    ActivitySecundBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    public void onBackPressed() {

        finish();
    }


}