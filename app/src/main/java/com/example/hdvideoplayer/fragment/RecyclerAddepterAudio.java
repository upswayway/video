package com.example.hdvideoplayer.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hdvideoplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAddepterAudio extends RecyclerView.Adapter<RecyclerAddepterAudio.viewaudioclass> {

    public interface onCallBack{
        void ClickItem(int position);
    }

    onCallBack onCallBack;
    private Context context;
    private List<AudioModel> list;
    AudioModel audioModel;


    public RecyclerAddepterAudio(Context context,onCallBack onCallBack) {
        this.context = context;
        list = new ArrayList<>();
        this.onCallBack =onCallBack;
    }

    @NonNull
    @Override
    public RecyclerAddepterAudio.viewaudioclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewaudio, parent, false);
        viewaudioclass viewaudio = new viewaudioclass(view);
        return viewaudio;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAddepterAudio.viewaudioclass holder, @SuppressLint("RecyclerView") int position) {
        audioModel = list.get(position);
        holder.audioname.setText(audioModel.getaName());
        File file = new File(audioModel.getaPath());
        Log.e("TAG", "onBindViewHolder: " + audioModel.getaPath());
        holder.andiotime.setText(getDuration(file));
        holder.audioname.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.ClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<AudioModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class viewaudioclass extends RecyclerView.ViewHolder {

        ImageView audioimg, menu;
        TextView andiotime, audioname;
        RelativeLayout audioplay;

        public viewaudioclass(@NonNull View itemView) {
            super(itemView);
            audioimg = itemView.findViewById(R.id.audioimg);
//            menu = itemView.findViewById(R.id.menu);
            andiotime = itemView.findViewById(R.id.andiotime);
            audioname = itemView.findViewById(R.id.audioname);
            audioplay = itemView.findViewById(R.id.audioplay);
        }
    }

    private static String getDuration(File file) {
        try {

            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return formateMilliSeccond(Long.parseLong(durationStr));
        } catch (Exception e) {
            return "";
        }
    }

    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }


}
