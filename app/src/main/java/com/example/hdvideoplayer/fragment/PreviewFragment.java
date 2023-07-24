package com.example.hdvideoplayer.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.hdvideoplayer.R;
import java.io.File;

public class PreviewFragment extends Fragment {


    public static PreviewFragment newInstance(String title) {
        PreviewFragment fragmentFirst = new PreviewFragment();
        Bundle args = new Bundle();
        args.putString("imagePath", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewimageclass, container, false);
        ImageView imageView=view.findViewById(R.id.im_display);
        String data = getArguments().getString("imagePath");
        Log.e("TAG", "onCreateView:....... "+data+"   " );
        Glide.with(requireActivity()).load(new File(data)).into(imageView);
        return view;

    }
}
