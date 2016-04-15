package com.uniquedu.myfilemanager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private ListView mListViewFiles;
    private File[] mAllFile;
    private LayoutInflater mInflater;
    private MyFileAdapter mAdapter;
    private Stack<File> mStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInflater=getLayoutInflater();
        mStack=new Stack<>();
        mListViewFiles= (ListView) findViewById(R.id.listview_files);
        File sdcard= Environment.getExternalStorageDirectory();
        mAllFile=sdcard.listFiles();
        mAdapter=new MyFileAdapter();
        mListViewFiles.setAdapter(mAdapter);
        mListViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file=mAllFile[position];
                mStack.push(file);//进入下一层时将此File压入堆栈
                mAllFile=file.listFiles();
                mAdapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&!mStack.isEmpty()){
            //此处返回上一层，堆栈弹出
            File file=mStack.pop();
            mAllFile=file.getParentFile().listFiles();
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyFileAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mAllFile.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh=null;
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.item_file,null);
                vh=new ViewHolder();
                vh.imageView= (ImageView) convertView.findViewById(R.id.imageview);
                vh.textViewName= (TextView) convertView.findViewById(R.id.textview_name);
                convertView.setTag(vh);
            }
            vh= (ViewHolder) convertView.getTag();
            vh.textViewName.setText(mAllFile[position].getName());
            if(mAllFile[position].isDirectory()){
                vh.imageView.setImageResource(R.mipmap.dir);
            }else{
                vh.imageView.setImageResource(R.mipmap.file);
            }
            return convertView;
        }
        class ViewHolder{
            ImageView imageView;
            TextView textViewName;
        }
    }

}
