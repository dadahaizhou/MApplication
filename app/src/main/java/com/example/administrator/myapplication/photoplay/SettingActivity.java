package com.example.administrator.myapplication.photoplay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.photoplay.adapter.PhotoAdapter;
import com.example.administrator.myapplication.photoplay.adapter.SongAdapter;
import com.example.administrator.myapplication.photoplay.bean.ImageBean;
import com.example.administrator.myapplication.photoplay.util.SharePreferenceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg_music_path)
    EditText edit_path;
    @BindView(R.id.btn_save_path)
    Button btn_save;
    Context con;
    String bgPath = "/PhotoMusic";
    @BindView(R.id.recycler_photo_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.bg_music_path_tv)
    TextView tv_bg_path;

    SongAdapter mAdapter;
    List dataList=new ArrayList();

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initEditData();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = edit_path.getText().toString();
                if (path != null && !path.isEmpty()) {
                    bgPath = path;
                    SharePreferenceUtil.setPrefString(con, "bgmusic", bgPath);

                } else {
                    Toast.makeText(con, "路径不可为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        initData();
    }
   void initData(){
       mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
       hd.sendEmptyMessageDelayed(2,15000);
       new Thread(new Runnable() {
           @Override
           public void run() {
               if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                   String path0 = Environment.getExternalStorageDirectory().getAbsolutePath();
                   Log.i("settingAct","root path0::"+path0);
                   File f=Environment.getExternalStorageDirectory();
                   hd.removeMessages(2);
                   fileSearch(f);
                   hd.sendEmptyMessage(1);


               }
               else{
                   hd.removeMessages(2);
                 hd.sendEmptyMessage(0);
               }
           }
       }).start();
   }

    void initEditData() {
        bgPath = SharePreferenceUtil.getPrefString(con, "bgmusic", bgPath);
        edit_path.setText(bgPath);
        tv_bg_path.setText(bgPath);
    }

    Handler hd=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mProgressDialog!=null)
                mProgressDialog.dismiss();
           switch (msg.what){
               case 1:
                   Log.i("settingAct","hd 1 dataList size:"+dataList.size());
                   setRecyclerView();
                   break;
               case 0:
                   Toast.makeText(con,"没有获取到本地文件夹",Toast.LENGTH_SHORT).show();
                   break;
           }
        }
    };
    public void setRecyclerView( ) {
       mRecyclerView.setLayoutManager(new LinearLayoutManager(con));
        mAdapter=new SongAdapter(this,dataList);
        mAdapter.setOnItemClickLitener(new SongAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageBean ib= (ImageBean) dataList.get(position);
                String path=ib.getTopImagePath();
                Log.i("settingAct","onItemClick foldername::"+path);
                if (path != null && !path.isEmpty()) {
                    bgPath = path;
                    SharePreferenceUtil.setPrefString(con, "bgmusic", bgPath);
                    tv_bg_path.setText(bgPath);

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
   void fileSearch(File f){
        if(f!=null&&f.isDirectory()){
        Log.i("settingAct","fileSearch foldername:"+f.getAbsolutePath());
         File[] fileArr=  f.listFiles();
            int  count=0;
            for (File f0: fileArr ){
                if(f0!=null&&f0.isFile()&&f0.getAbsolutePath().toLowerCase().endsWith(".mp3")){
                 count++;
                }
                else{
                    fileSearch(f0);
                }
            }
            if(count>0){
                ImageBean ib=new ImageBean();
                ib.setImageCounts(count);
                ib.setFolderName(f.getName());
                ib.setTopImagePath(f.getAbsolutePath());
                dataList.add(ib);
            }

        }

    }
}
