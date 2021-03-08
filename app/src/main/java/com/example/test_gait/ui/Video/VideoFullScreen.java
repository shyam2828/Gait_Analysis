package com.example.test_gait.ui.Video;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_gait.R;

public class VideoFullScreen extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);
        webView=findViewById(R.id.full_video);

        String link=getIntent().getStringExtra("link");
        webView.loadUrl(link);

    }

}