package com.uniquedu.mysdcard;

import android.os.Environment;
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
    private EditText mEditTextSdcard;
    private Button mButtonSave;
    private Button mButtonRead;
    private TextView mTextViewSdcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextSdcard= (EditText) findViewById(R.id.edittext_sdcard);
        mButtonSave= (Button) findViewById(R.id.button_save);
        mButtonRead= (Button) findViewById(R.id.button_read);
        mTextViewSdcard= (TextView) findViewById(R.id.textview_read);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToSdcard();
            }
        });
        mButtonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromSdcard();

            }
        });
    }

    /**
     * 读取sdcard中的内容
     */
    private void readFromSdcard() {
        //得到要读取文件的路径
        File sdcard= Environment.getExternalStorageDirectory();
        File saveFile=new File(sdcard,"save.txt");
        if(saveFile.exists()){
            try {
                InputStream inputStream=new FileInputStream(saveFile);
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String content="";
                String line=bufferedReader.readLine();
                while(line!=null){
                    content=content.concat(line);
                    line=bufferedReader.readLine();
                }
                mTextViewSdcard.setText(content);
                bufferedReader.close();
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToSdcard() {
        //存储内容到外部sdcard
        //先得到sdcard的路径 /mnt/sdcard
        File sdcard= Environment.getExternalStorageDirectory();
        File saveFile=new File(sdcard,"save.txt");
        //创建保存数据的文件
        try {
            if(!saveFile.exists()) {
                saveFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            OutputStream outputStream=new FileOutputStream(saveFile);
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(outputStream));
            writer.write(mEditTextSdcard.getText().toString());
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
