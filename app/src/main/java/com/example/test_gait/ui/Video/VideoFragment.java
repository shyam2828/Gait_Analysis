package com.example.test_gait.ui.Video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_gait.R;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<DataSetList> arrayList;
    private Button fullscreen;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_video, container, false);


        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<DataSetList>();


        DataSetList dataSetList=new DataSetList("https://youtu.be/XMbU1A5QLzg");
        arrayList.add(dataSetList);

        dataSetList=new DataSetList("https://youtu.be/zWxRWQAWVBw");
        arrayList.add(dataSetList);

        YoutubeAdapter youtubeAdapter=new YoutubeAdapter(arrayList,getContext());
        recyclerView.setAdapter(youtubeAdapter);



        return view;
    }




}