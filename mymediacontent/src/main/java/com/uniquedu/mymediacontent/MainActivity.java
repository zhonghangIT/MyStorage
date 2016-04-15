package com.uniquedu.mymediacontent;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonImg;
    private Button mButtonAudio;
    private Button mButtonVedio;
    private ListView mListView;
    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonImg = (Button) findViewById(R.id.button_img);
        mButtonAudio = (Button) findViewById(R.id.button_audio);
        mButtonVedio = (Button) findViewById(R.id.button_vedio);
        mListView = (ListView) findViewById(R.id.listview_content);
        mButtonImg.setOnClickListener(this);
        mButtonAudio.setOnClickListener(this);
        mButtonVedio.setOnClickListener(this);
        mResolver = getContentResolver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_img:
                selectAllImage();

                break;
            case R.id.button_audio:
                selectAllAudio();
                break;
            case R.id.button_vedio:
                selectAllVedio();
                break;
        }
    }
    private void selectAllVedio() {
        String[] data=new String[]{MediaStore.Video.Media._ID,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DATA};
        Cursor videoCursor = mResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data, null, null, null);
        ArrayList<String > allVideoPaths=new ArrayList<>();
        while (videoCursor.moveToNext()) {
            Log.d("", "视频的id" + videoCursor.getString(0) + "   视频名称" + videoCursor.getString(1) + "   视频绝对路径" + videoCursor.getString(2));
            allVideoPaths.add(videoCursor.getString(2));
        }
        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,allVideoPaths);
        mListView.setAdapter(adapter);
    }
    private void selectAllAudio() {
        String[] data=new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA};
        Cursor audioCursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, data, null, null, null);
        ArrayList<String > allAudioPaths=new ArrayList<>();
        while (audioCursor.moveToNext()) {
            Log.d("", "音频的id" + audioCursor.getString(0) + "   音频名称" + audioCursor.getString(1) + "   音频绝对路径" + audioCursor.getString(2));
            allAudioPaths.add(audioCursor.getString(2));
        }
        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,allAudioPaths);
        mListView.setAdapter(adapter);
    }

    private void selectAllImage() {
        //通过android系统提供的contentProvider得到所有的sdcard中的图片
        //查询contentProvider的方法需要得到ContentResolver
        //第一个Uri 第二个查询的内容
        String[] data = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
        Cursor imageCursor = mResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, data, null, null, null);
        ArrayList<String > allImagPaths=new ArrayList<>();
        while (imageCursor.moveToNext()) {
            Log.d("", "图片的id" + imageCursor.getString(0) + "   图片名称" + imageCursor.getString(1) + "   图片绝对路径" + imageCursor.getString(2));
            allImagPaths.add(imageCursor.getString(2));
        }
        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,allImagPaths);
        mListView.setAdapter(adapter);
    }
}
