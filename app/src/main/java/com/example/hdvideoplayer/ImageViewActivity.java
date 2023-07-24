package com.example.hdvideoplayer;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.hdvideoplayer.databinding.ActivityImageViewBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    private ArrayList<String> imageData = new ArrayList<String>();
    private int position;
    ActivityImageViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        imageData = getIntent().getStringArrayListExtra("img");
        position = getIntent().getIntExtra("position", 0);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), ImageViewActivity.this, imageData);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.viewpager.setCurrentItem(position);


        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initedit();
            }
        });
        binding. btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initshare();
            }
        });

        binding.btnInformetion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initinformetion();

            }
        });
    }

    private void initedit() {

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(imageData.get(position)));
        } else {
            uri = Uri.fromFile(new File(imageData.get(position)));
        }
        Intent editIntent = new Intent(Intent.ACTION_EDIT);
        Log.e("FSgsdfgds", "" + imageData.get(position));

        editIntent.setDataAndType(uri, "image/*");
        int flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION;
        editIntent.setFlags(flags);
        startActivity(Intent.createChooser(editIntent, null));


    }

    private void initshare() {
        Intent intentMore = new Intent(Intent.ACTION_SEND);
        intentMore.setType("image/*");
        try {
            intentMore.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), imageData.get(position), "", "")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(Intent.createChooser(intentMore, "Share Image"));

    }

    private void initinformetion() {
        TextView imagename;
        TextView imagestoreg;
        TextView imagesize;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customdialoge_view);
        imagename = (TextView) dialog.findViewById(R.id.imagename);
        imagestoreg = (TextView) dialog.findViewById(R.id.imagestoreg);
        imagesize = (TextView) dialog.findViewById(R.id.imagesize);
        File file = new File(imageData.get(binding.viewpager.getCurrentItem()));
        imagename.setText(file.getName());
        imagestoreg.setText(file.getPath());
        imagesize.setText("" + getSize(file));
        dialog.show();
    }
    private String getSize(File file) {
        long fileSizeInBytes = file.length();
        String s;
        if (fileSizeInBytes >= 1073741824) {
            s = ((fileSizeInBytes / 1073741824) + " GB");
        } else if (fileSizeInBytes >= 1048576) {
            s = ((fileSizeInBytes / 1048576) + " MB");
        } else if (fileSizeInBytes >= 1024) {
            s = ((fileSizeInBytes / 1024) + " KB");
        } else if (fileSizeInBytes > 1) {
            s = (fileSizeInBytes + " bytes");
        } else if (fileSizeInBytes == 1) {
            s = (fileSizeInBytes + " byte");
        } else {
            s = ("0 bytes");
        }
        return s;
    }


    @Override
    public void onBackPressed() {


            super.onBackPressed();


    }
}