package com.example.zhou.tool;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/6/26.
 */

public class GlideUtil {
//    static RequestOptions options = new RequestOptions()
//            .centerCrop()
////            .override(300,800)
//            .placeholder(R.mipmap.ic_launcher)
//            .error(R.mipmap.ic_launcher)
//            .priority(Priority.HIGH)
//            .diskCacheStrategy(DiskCacheStrategy.NONE);
    public static void  loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

}
