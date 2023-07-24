package com.example.hdvideoplayer.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hdvideoplayer.ImageViewActivity;
import com.example.hdvideoplayer.R;
import com.example.hdvideoplayer.VideoPlayActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class RecyclerAddepterImage extends RecyclerView.Adapter<RecyclerAddepterImage.viewimageclass> {

    public interface onCallBack{
        void ClickItem(int position);
    }
    onCallBack onCallBack;
    Context context;
    private ArrayList<String> imageData = new ArrayList<String>();
    public RecyclerAddepterImage(Context context,ArrayList<String> imageData, onCallBack onCallBack) {
        this.imageData = imageData;
        this.context = context;
        this.onCallBack=onCallBack;
    }
    @NonNull
    @Override
    public viewimageclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewimage, parent, false);
        viewimageclass viewimageclass = new viewimageclass(view);
        return viewimageclass;
    }

    @Override
    public void onBindViewHolder(@NonNull viewimageclass holder, @SuppressLint("RecyclerView") int position) {

        String imagePath = imageData.get(position);
        if (imagePath != null)
        {
            Glide.with(context).load(new File(imageData.get(position))).placeholder(R.drawable.audio).into(holder.viewimage);
        }
        else
        {
            Toast.makeText(context, "Images Empty", Toast.LENGTH_SHORT).show();
        }

        holder. viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCallBack.ClickItem(position);


            }
        });
    }

    @Override
    public int getItemCount() {
        return imageData.size();
    }

    public class viewimageclass extends RecyclerView.ViewHolder {

        ImageView viewimage;
        CardView imageview;
        public viewimageclass(@NonNull View itemView) {
            super(itemView);
            viewimage = itemView.findViewById(R.id.viewimage);
            imageview = itemView.findViewById(R.id.imageview);
        }
    }
}
