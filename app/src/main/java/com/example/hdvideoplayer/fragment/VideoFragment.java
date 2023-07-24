package com.example.hdvideoplayer.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hdvideoplayer.R;
import com.example.hdvideoplayer.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;
public class VideoFragment extends Fragment {

    private View view;
    public static List<Videomodel> demolist = new ArrayList<>();
    private RecyclerView reclyviewvideo;
    private TextView textVideo;
    private ProgressBar progressVideo;
    RecyclerAddepterVideo recycler_addepter_video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_video, container, false);
            initView();
            recycler_addepter_video = new RecyclerAddepterVideo(requireContext(), new RecyclerAddepterVideo.onCallBack() {
                @Override
                public void ClickItem(int position) {


                        try {
                            Intent intent=new Intent(requireContext(), VideoPlayActivity.class);
                            intent.putExtra("plays",position);
                            requireContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("#TAG", "onClick: "+e.getMessage() );
                        }

                }
            }) ;
            reclyviewvideo.setAdapter(recycler_addepter_video);
            new AsyncTaskVideo().execute();
            return view;
    }

    private class AsyncTaskVideo extends AsyncTask<Void, Void, List<Videomodel>> {

        @Override
        protected void onPreExecute() {
            progressVideo.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<Videomodel> doInBackground(Void... voids) {
            demolist=getAllAudioFromDevice(getActivity());
            return demolist;
        }

        @Override
        protected void onPostExecute(List<Videomodel> videomodel) {
            super.onPostExecute(videomodel);

            if (progressVideo.getVisibility() == View.VISIBLE) {

                if (videomodel.size() > 0) {
                    textVideo.setVisibility(View.GONE);
                    reclyviewvideo.setVisibility(View.VISIBLE);
                    recycler_addepter_video.update(videomodel);
                    progressVideo.setVisibility(View.GONE);
                } else {
                    textVideo.setVisibility(View.VISIBLE);
                    reclyviewvideo.setVisibility(View.GONE);
                    progressVideo.setVisibility(View.GONE);
                }
            }
        }
    }

    public List<Videomodel> getAllAudioFromDevice(final Context context) {
        final List<Videomodel> tempAudioList = new ArrayList<>();
        ContentResolver musicResolver;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        //Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Video.VideoColumns.ARTIST, MediaStore.Video.Media.DURATION};
        Cursor videoCursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (videoCursor != null && videoCursor.moveToFirst()) {
            //get columns

            int titleColumn = videoCursor.getColumnIndex
                    (MediaStore.Video.Media.TITLE);
            int idColumn = videoCursor.getColumnIndex
                    (MediaStore.Video.Media._ID);
            int artistColumn = videoCursor.getColumnIndex
                    (MediaStore.Video.Media.ARTIST);

            if (videoCursor != null) {
                while (videoCursor.moveToNext()) {
                    Videomodel videomodel;
                    String path = videoCursor.getString(0);
                    String album = videoCursor.getString(1);
                    String artist = videoCursor.getString(2);
                    long dure = videoCursor.getLong(3);
                    String uriSound = videoCursor.getString(0);
                    Log.e("TAGURI", "getAllAudioFromDevice: " + path + "\n");

                    String name = path.substring(path.lastIndexOf("/") + 1);

                    videomodel = new Videomodel(name, path, album, artist, dure);

                    tempAudioList.add(videomodel);
                }
                videoCursor.close();
            }
        }
        return tempAudioList;
    }

    private void initView() {
        reclyviewvideo = (RecyclerView) view.findViewById(R.id.reclyviewvideo);
        textVideo = (TextView) view.findViewById(R.id.text_video);
        progressVideo = (ProgressBar) view.findViewById(R.id.progress_video);
    }
}