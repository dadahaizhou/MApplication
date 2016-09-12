package com.example.zhou.myapplication.photoplay.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.zhou.myapplication.photoplay.bean.ImageBean;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/10.
 */
public class FileUtils {

    public static HashMap<String, ImageBean> scanAllAudioFiles(Context context) {
//生成动态数组，并且转载数据
        HashMap<String, ImageBean> mylist = new HashMap<String, ImageBean>();

//查询媒体数据库
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
//遍历媒体数据库
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                //歌曲编号
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//                if(size>1024*800){//大于800K
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("musicId", id);
//                    map.put("musicTitle", tilte);
//                    map.put("musicFileUrl", url);
//                    map.put("music_file_name", tilte);
//                    mylist.add(map);
//                }
                String folderurl = url.substring(0, url.lastIndexOf("/"));
                if (url.toLowerCase().endsWith(".mp3")) {
                    if (!mylist.containsKey(folderurl)) {
                        ImageBean ib = new ImageBean();
                        ib.setImageCounts(1);
                        ib.setFolderName(folderurl.substring(folderurl.lastIndexOf("/")));
                        ib.setTopImagePath(folderurl);
                        mylist.put(folderurl, ib);
                    } else {
                        mylist.get(folderurl).ImageCountsIncrease();
                    }
                }
                Log.i("settingAct", "scanAllAudioFiles url::" + url + "\n folderurl:" + folderurl);
                //Log.i("settingAct","scanAllAudioFiles  folderurl:"+folderurl);
                cursor.moveToNext();
            }
        }
        return mylist;
    }
}
