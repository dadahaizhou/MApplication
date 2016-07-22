package com.example.administrator.myapplication.photoplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.photoplay.bean.ImageBean;
import com.example.administrator.myapplication.photoplay.util.NativeImageLoader;
import com.example.administrator.myapplication.photoplay.view.MyImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    Context context;
    List dataList=new ArrayList();
    public SongAdapter(Context con, List list){
        this.context=con;
        dataList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_reccle_song, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       ImageBean photo= (ImageBean) dataList.get(position);
        final String path=photo.getTopImagePath();
        Log.i("song","click path:"+path);

        //Picasso.with(context).load(url).into(holder.img);

        holder.tv_path.setText(""+photo.getTopImagePath());
        holder.tv_count.setText(photo.getImageCounts()+"首");
        holder.tv_name.setText(photo.getFolderName());
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView tv_count,tv_path;
        public TextView tv_name;
        RelativeLayout rel;
        public MyViewHolder(View view)
        {
            super(view);
            img = (ImageView) view.findViewById(R.id.group_image);
            tv_count= (TextView) view.findViewById(R.id.count);
            tv_name= (TextView) view.findViewById(R.id.name);
            tv_path= (TextView) view.findViewById(R.id.spath);
            rel= (RelativeLayout) view.findViewById(R.id.item_song_content);



        }
    }
}
