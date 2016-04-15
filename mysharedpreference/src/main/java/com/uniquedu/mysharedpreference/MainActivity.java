package com.uniquedu.mysharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextUserName= (EditText) findViewById(R.id.edittext_username);
        mEditTextPassword= (EditText) findViewById(R.id.edittext_password);
        mButtonSave= (Button) findViewById(R.id.button_save);
        readPreference();
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writePreference();
            }
        });
    }

    /**
     * 向preference中写入数据
     */
    private void writePreference() {
        String userName=mEditTextUserName.getText().toString();
        String password=mEditTextPassword.getText().toString();
        //使用sharedpreference存储
        //传递两个参数，一个是preference的名称,preference的使用方式，使用方式主要使用私有的，即只有本应用能够访问此数据
//                SharedPreferences preferences=getPreferences(MODE_PRIVATE);//使用这种不指定名称的方式，会给出一个默认名称，此处默认名称为MainActivity.xml
        SharedPreferences preferences=getSharedPreferences("mypreference",MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();//存储使用editor对象去存储数据
        editor.putString("userName",userName);
        editor.putString("password",password);
        editor.commit();//最后一定记得提交
    }

    /**
     * 读取preference的数据
     */
    private void readPreference() {
        //读取sharedpreference中的内容
        SharedPreferences preferences=getSharedPreferences("mypreference",MODE_PRIVATE);
        String userName=preferences.getString("userName", "");
        String password=preferences.getString("password","");
        mEditTextUserName.setText(userName);
        mEditTextPassword.setText(password);
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
