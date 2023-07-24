package com.example.hdvideoplayer.fragment;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hdvideoplayer.AudioPlayActivity;
import com.example.hdvideoplayer.R;

import java.util.ArrayList;
import java.util.List;

public class AudioFragment extends Fragment {

    View view;
    private RecyclerView reclyviewaudio;
    public static List<AudioModel> modelList = new ArrayList<>();
    private Button play;
    private Button pasue;
    private Button stop;
    private TextView textAudio;
    private ProgressBar progressAudio;
    RecyclerAddepterAudio adepterAudio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_audio, container, false);
        initView();
        adepterAudio = new RecyclerAddepterAudio(requireContext(), new RecyclerAddepterAudio.onCallBack() {
            @Override
            public void ClickItem(int position) {


                    try {
                        Intent intent=new Intent(requireContext(), AudioPlayActivity.class);
                        intent.putExtra("play",position);
                        requireContext().startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("#TAG", "onClick: "+e.getMessage() );
                    }


            }
        });
        reclyviewaudio.setAdapter(adepterAudio);
        new AsyncTaskAudio().execute();
        return view;
    }

    private class AsyncTaskAudio extends AsyncTask<Void, Void, List<AudioModel>> {

        @Override
        protected void onPreExecute() {
            progressAudio.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected List<AudioModel> doInBackground(Void... voids) {
            modelList=getAllAudioFromDevice(getActivity());
            return modelList;
        }
        @Override
        protected void onPostExecute(List<AudioModel> audioModelList) {
            super.onPostExecute(audioModelList);
            if (progressAudio.getVisibility() == View.VISIBLE) {
                if (audioModelList.size() > 0) {
                    textAudio.setVisibility(View.GONE);
                    reclyviewaudio.setVisibility(View.VISIBLE);
                    adepterAudio.update(audioModelList);
                    progressAudio.setVisibility(View.GONE);
                }
                else
                {
                    textAudio.setVisibility(View.VISIBLE);
                    reclyviewaudio.setVisibility(View.GONE);
                    progressAudio.setVisibility(View.GONE);
                }
            }
        }
    }
    public List<AudioModel> getAllAudioFromDevice(final Context context) {
        final List<AudioModel> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.Media.DURATION};
        Cursor musicCursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);

            if (musicCursor != null) {
                while (musicCursor.moveToNext()) {
                    AudioModel audioModel;
                    String path = musicCursor.getString(0);
                    String album = musicCursor.getString(1);
                    String artist = musicCursor.getString(2);
                    String dure = musicCursor.getString(3);
                    String uriSound = musicCursor.getString(0);
                    Log.e("TAGURI", "getAllAudioFromDevice: " + path + "\n");

                    String name = path.substring(path.lastIndexOf("/") + 1);
                    audioModel = new AudioModel(name, path, album, artist);
                    Log.e("Name :" + name, " Album :" + album + uri + path);
                    Log.e("Path :" + path, " Artist :" + artist);

                    tempAudioList.add(audioModel);
                }
                musicCursor.close();
            }
        }
        return tempAudioList;
    }
    private void initView() {
        reclyviewaudio = (RecyclerView) view.findViewById(R.id.reclyviewaudio);
        textAudio = (TextView) view.findViewById(R.id.text_audio);
        progressAudio = (ProgressBar) view.findViewById(R.id.progress_audio);
    }
}