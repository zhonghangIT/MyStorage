package com.uniquedu.mysqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonCreate;
    private Button mButtonUpgrade;
    private Button mButtonCreateTable;
    private EditText mEditTextName;
    private EditText mEditTextSex;
    private EditText mEditTextAge;
    private Button mButtonInsert;
    private SQLiteDatabase mDatabase;
    private Button mButtonUpdate;
    private Button mButtonDelete;
    private Button mButtonSelect;
    private ListView mListViewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonCreate = (Button) findViewById(R.id.button_create_database);
        mButtonUpgrade = (Button) findViewById(R.id.button_upgrade_database);
        mButtonCreateTable = (Button) findViewById(R.id.button_create_table);
        mButtonUpdate = (Button) findViewById(R.id.button_update);
        mButtonInsert = (Button) findViewById(R.id.button_insert);
        mEditTextName = (EditText) findViewById(R.id.edittext_name);
        mEditTextSex = (EditText) findViewById(R.id.edittext_sex);
        mEditTextAge = (EditText) findViewById(R.id.edittext_age);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mButtonSelect = (Button) findViewById(R.id.button_select);
        mListViewStudent = (ListView) findViewById(R.id.listview_student);
        mButtonSelect.setOnClickListener(this);
        mButtonInsert.setOnClickListener(this);
        mButtonCreateTable.setOnClickListener(this);
        mButtonCreate.setOnClickListener(this);
        mButtonUpgrade.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        createSqlite();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_database:
//                createSqlite();
                break;
            case R.id.button_upgrade_database:
//                upgradeDatabase();
                break;
            case R.id.button_create_table:
                //创建表
//                MyOpenHelper
//                MyOpenHelper openHelper=new MyOpenHelper(getApplicationContext(),"TEST.DB",null,1);
//                openHelper.getWritableDatabase();//调用创建数据库,会自动调用onCreate.  Database分为只读。可写两种
                break;
            case R.id.button_insert:
                insertToTable();

                break;
            case R.id.button_update:
                updateTable();
                break;
            case R.id.button_delete:
                mDatabase.delete("student", "name=?", new String[]{"lisi"});
                break;
            case R.id.button_select:
                Cursor cursor = mDatabase.rawQuery("select * from student", null);//查询数据库的数据结果集
//                cursor.moveToFirst();
//                while(!cursor.isAfterLast()){
//                    Log.d("selectSql","查询到的学生的姓名："+cursor.getString(cursor.getColumnIndex("name")));
//                    cursor.moveToNext();
//                }
//                cursor.close();

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.item_student,
                        cursor, new String[]{"_id", "name", "sex", "age"},
                        new int[]{R.id.textview_id, R.id.textview_name, R.id.textview_sex, R.id.textview_age});
                mListViewStudent.setAdapter(adapter);
                //Android中如果使用SimpleCursorAdapter，那么在cursor必须要_id的字段
                break;
            default:
                break;
        }
    }

    private void updateTable() {
        ContentValues values = new ContentValues();
        values.put("name", "lisi");
        mDatabase.update("student", values, "_id=? and age=?", new String[]{"2", "20"});
    }

    private void insertToTable() {
        //插入数据库使用ContentValue
        ContentValues values = new ContentValues();
        values.put("name", mEditTextName.getText().toString());
        values.put("sex", mEditTextSex.getText().toString());
        values.put("age", mEditTextAge.getText().toString());
        mDatabase.insert("student", null, values);
    }

    private void upgradeDatabase() {
        MyOpenHelper openHelper = new MyOpenHelper(getApplicationContext(), "TEST.DB", null, 2);
        openHelper.getWritableDatabase();//调用创建数据库
    }

    private void createSqlite() {
        MyOpenHelper openHelper = new MyOpenHelper(getApplicationContext(), "TEST.DB", null, 1);
        mDatabase = openHelper.getWritableDatabase();//调用创建数据库
    }
}
