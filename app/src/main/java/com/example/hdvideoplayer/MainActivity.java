package com.example.hdvideoplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hdvideoplayer.databinding.ActivityMainBinding;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();

    }

    private void initView() {


        binding.btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Permissions.check(MainActivity.this/*context*/, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
                        intent.putExtra("click", 0);
                        startActivity(intent);
                    }
                });

            }
        });
        binding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Permissions.check(MainActivity.this/*context*/, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
                        intent.putExtra("click", 1);
                        startActivity(intent);
                    }
                });

            }
        });
        binding.btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Permissions.check(MainActivity.this/*context*/, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                        @Override
                        public void onGranted() {
                            Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
                            intent.putExtra("click", 2);
                            startActivity(intent);
                        }
                    });

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
