package com.example.hdvideoplayer.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hdvideoplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAddepterVideo extends RecyclerView.Adapter<RecyclerAddepterVideo.viewvideoclass> {

    public interface onCallBack{
        void ClickItem(int position);
    }
    onCallBack onCallBack;
    Context context;
    Videomodel videomodel;
    private List<Videomodel> list;
    private Object duration;

    public RecyclerAddepterVideo(Context context,onCallBack onCallBack) {
        this.context = context;
        list = new ArrayList<>();
        this.onCallBack=onCallBack;
    }

    @NonNull
    @Override
    public RecyclerAddepterVideo.viewvideoclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewvideo, parent, false);
        viewvideoclass viewvideo = new viewvideoclass(view);
        return viewvideo;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAddepterVideo.viewvideoclass holder, @SuppressLint("RecyclerView") int position) {


        videomodel = list.get(position);
        holder.videoname.setText(videomodel.getName());

        holder.videotime.setText(convertMillieToHMmSs(videomodel.getDure()));
        Log.e("TAG==", "onBindViewHolder: "+videomodel );

        File file = new File(videomodel.getPath());
        Glide.with(context).load(file.getPath()).centerCrop().placeholder(R.color.black).into(holder.videoimg);

        holder.videoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              onCallBack.ClickItem(position);



            }
        });

    }

    public static String convertMillieToHMmSs(long millie)
    {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
        else {
            return String.format("%02d:%02d" , minute, second);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Videomodel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class viewvideoclass extends RecyclerView.ViewHolder {

        ImageView videoimg;
        TextView videoname, videotime;
        RelativeLayout videoplay;

        public viewvideoclass(@NonNull View itemView) {
            super(itemView);
            videoimg = itemView.findViewById(R.id.videoimg);
            videoname = itemView.findViewById(R.id.videoname);
            videotime = itemView.findViewById(R.id.videotime);
            videoplay = itemView.findViewById(R.id.videoplay);
        }
    }
}
