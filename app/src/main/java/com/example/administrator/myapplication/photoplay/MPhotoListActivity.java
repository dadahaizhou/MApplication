package com.example.administrator.myapplication.photoplay;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.photoplay.adapter.PhotoAdapter;
import com.example.administrator.myapplication.photoplay.bean.ImageBean;
import com.example.administrator.myapplication.photoplay.util.FileUtils;
import com.example.administrator.myapplication.photoplay.util.SharePreferenceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MPhotoListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_photo_list)
    RecyclerView mRecyclerView;

    PhotoAdapter mAdapter;
    List dataList=new ArrayList();
    private ProgressDialog mProgressDialog;
    private final static int SCAN_OK = 1;
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;

        setContentView(R.layout.activity_mphoto_list);
        ButterKnife.bind(this);
        Log.i("photo","onCreate toolbar:"+toolbar);
        setSupportActionBar(toolbar);

        setMToolBar();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        initData();

    }

   void initData(){
           getImages();
       initLocalMusicFolderList();
    }
  void  setMToolBar(){
      toolbar.setTitle("选择相册");
      toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
              switch (item.getItemId()){
                  case  R.id.action_search:
                      Toast.makeText(con,"action_search",Toast.LENGTH_SHORT).show();
                  break;
                  case  R.id.action_notification:
                      Toast.makeText(con,"action_notification",Toast.LENGTH_SHORT).show();

                      break;
                  case  R.id.action_setting:
                      startActivity(new Intent(con,SettingActivity.class));
                      break;

              }
              return true;
          }
      });
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return  true;
       // return super.onCreateOptionsMenu(menu);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImages() {
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MPhotoListActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                mCursor.close();
            }
        }).start();

    }


    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
        Log.i("photo","subGroupOfImage mGruopMap.size():"+mGruopMap.size());
//        if(mGruopMap.size() == 0){
//            return null;
//        }
        List<ImageBean> list = new ArrayList<ImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }
        Log.i("photo","subGroupOfImage list.size():"+ list.size());
        return list;

    }
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    mProgressDialog.dismiss();
                    setAdapter();

                    break;
            }
        }

    };

  void  setAdapter(){
      dataList=subGroupOfImage(mGruopMap);
      mAdapter=new PhotoAdapter(this,dataList);
      mAdapter.setOnItemClickLitener(new PhotoAdapter.OnItemClickLitener() {
          @Override
          public void onItemClick(View view, int position) {
              ImageBean ib= (ImageBean) dataList.get(position);
              Log.i("photo","onItemClick foldername::"+ib.getFolderName());
              Intent in=new Intent(MPhotoListActivity.this,PlayPhotoActivity.class);
              ArrayList<String> alist= (ArrayList<String>) mGruopMap.get(ib.getFolderName());
              in.putStringArrayListExtra("photoList",alist);
              startActivity(in);
          }

          @Override
          public void onItemLongClick(View view, int position) {

          }
      });
      mRecyclerView.setAdapter(mAdapter);
  }
    public void initLocalMusicFolderList(){
       String path= SharePreferenceUtil.getPrefString(con, "bgmusic", "");
        Log.i("MPhotoListActivity"," initLocalMusicFolderList foldername::"+path);
        if(!TextUtils.isEmpty(path)) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, ImageBean> folders=  FileUtils.scanAllAudioFiles(con);
                Iterator<Map.Entry<String, ImageBean>> iter=folders.entrySet().iterator();
                while (iter.hasNext()){
                    Map.Entry<String, ImageBean> entry=iter.next();
                     String path= entry.getValue().getTopImagePath();
                    Log.i("MPhotoListActivity","thread initLocalMusicFolderList foldername::"+path);
                    if (path != null && !path.isEmpty()) {
                        SharePreferenceUtil.setPrefString(con, "bgmusic", path);
                        break;
                    }
                }

            }
        }).start();
    }
}
