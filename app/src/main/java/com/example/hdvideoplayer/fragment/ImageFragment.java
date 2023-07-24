package com.example.hdvideoplayer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hdvideoplayer.ImageViewActivity;
import com.example.hdvideoplayer.R;

import java.util.ArrayList;

public class ImageFragment extends Fragment {

    RecyclerAddepterImage adapter;
    private RecyclerView reclyViewImage;
    private TextView textPhoto;
    private ProgressBar progress;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        initView();
        reclyViewImage.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        reclyViewImage.setLayoutManager(layoutManager);
        MyTask myTask = new MyTask();
        myTask.execute();
        return view;
    }

    private void initView() {
        reclyViewImage = (RecyclerView) view.findViewById(R.id.recly_view_image);
        textPhoto = (TextView) view.findViewById(R.id.text_photo);
        progress = (ProgressBar) view.findViewById(R.id.progress);
    }

    private class MyTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            return getAllShownImagesPath(getActivity());
        }

        @Override
        protected void onPostExecute(ArrayList<String> imageList) {
            super.onPostExecute(imageList);


            if (progress.getVisibility() == View.VISIBLE) {
                if (imageList.size() > 0) {
                    textPhoto.setVisibility(View.GONE);
                    reclyViewImage.setVisibility(View.VISIBLE);
                    adapter = new RecyclerAddepterImage(requireActivity(), imageList, new RecyclerAddepterImage.onCallBack() {
                        @Override
                        public void ClickItem(int position) {
                            Intent intent = new Intent(requireContext(), ImageViewActivity.class);
                            intent.putExtra("img", imageList);
                            intent.putExtra("position", position);
                            startActivity(intent);


                        }
                    });
                    reclyViewImage.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                } else {
                    textPhoto.setVisibility(View.VISIBLE);
                    reclyViewImage.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                }
            }
        }
    }



    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.Media.DATA};

        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}