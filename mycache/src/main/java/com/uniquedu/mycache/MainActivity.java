package com.uniquedu.mycache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextCache;
    private Button mButtonWrite;
    private Button mButtonRead;
    private TextView mTextViewCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextCache= (EditText) findViewById(R.id.edittext_cache);
        mButtonWrite= (Button) findViewById(R.id.button_write);
        mButtonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                writeToCache();
                writeToFiles();
            }
        });
        mButtonRead= (Button) findViewById(R.id.button_read);
        mTextViewCache= (TextView) findViewById(R.id.textview_cache);
        mButtonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                readFromCache();
                readFromFiles();
            }
        });
    }

    /**
     * 读取内部存储中files文件夹下的内容
     */
    private void readFromFiles() {
        try {
           InputStream inputStream= openFileInput("myCacheFile.txt");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String content="";
            String line=bufferedReader.readLine();
            while(line!=null){
                content=content.concat(line);
                line=bufferedReader.readLine();
            }
            mTextViewCache.setText(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入内部存储中的files文件夹下
     */
    private void writeToFiles() {
        try {
            OutputStream outputStream=openFileOutput("myCacheFile.txt",MODE_PRIVATE);
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(outputStream));
            writer.write(mEditTextCache.getText().toString());
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取内部存储中缓存文件夹下的内容
     */
    private void readFromCache() {
        //读取内部存储中缓存文件夹下的内容
        File cacheDir=getCacheDir();
        File myCache=new File(cacheDir,"myCache.text");
        if(myCache.isFile()){
            try {
                InputStream inputStream=new FileInputStream(myCache);
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String content="";
                String line=bufferedReader.readLine();
                while (line!=null){
                    content=content.concat(line);
                    line=bufferedReader.readLine();
                }
                mTextViewCache.setText(content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向内部存储中的缓存文件夹下写入内容
     */
    private void writeToCache() {
        //向内部存储中写入内容
        File cacheDir=getCacheDir();//Context中提供的方法得到内部存储中的缓存文件夹
        File myCache=new File(cacheDir,"myCache.text");
        try {
            myCache.createNewFile();//创建新文件

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            OutputStream outputStream=new FileOutputStream(myCache);
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(outputStream));
            writer.write(mEditTextCache.getText().toString());
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
