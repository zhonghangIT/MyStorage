package com.uniquedu.mycontact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mButtonSelect;
    private static final String FLAG="selectcontact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonSelect = (Button) findViewById(R.id.button_select);
        mButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先查询到联系人的id，再根据id查询详细内容
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID}, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    //得到了联系人的id
                    Cursor detailCursor=resolver.query(ContactsContract.Data.CONTENT_URI,new String[]{
                            ContactsContract.Data._ID, ContactsContract.Data.MIMETYPE, ContactsContract.Data.DATA1}
                            //使用联系人的id查询联系人的详细内容，这些详细内容在同一个表中通过不同的类型存储了不同的数据
                    , ContactsContract.Data.CONTACT_ID+"=?",new String[]{id},null);
                    while(detailCursor.moveToNext()){
                        String mimeType=detailCursor.getString(1);
                        String content=detailCursor.getString(2);
                        //这里是得到联系人的一条详细信息
                        if(mimeType.contains("/name")){
                            Log.d(FLAG,"姓名："+content);
                        }else if(mimeType.contains("/phone")){
                            Log.d(FLAG,"电话："+content);
                        }
                    }
                    Log.d(FLAG,".................以上为一个联系人的详细信息");
                    detailCursor.close();

                }
                cursor.close();
            }
        });
    }
}
