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
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.photoplay.bean.ImageBean;
import com.example.administrator.myapplication.photoplay.util.NativeImageLoader;
import com.example.administrator.myapplication.photoplay.view.MyImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    Context context;
    List dataList=new ArrayList();
    public PhotoAdapter(Context con,List list){
        this.context=con;
        dataList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_reccle_photo, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       ImageBean photo= (ImageBean) dataList.get(position);
        final String path=photo.getTopImagePath();
        Log.i("photo","click path:"+path);

        //Picasso.with(context).load(url).into(holder.img);
        holder.img.setOnMeasureListener(new MyImageView.OnMeasureListener() {
            @Override
            public void onMeasureSize(int width, int height) {
                mPoint.set(width, height);
            }
        });
        holder.tv_count.setText(""+photo.getImageCounts());
        holder.mTextViewTitle.setText(photo.getFolderName()+"("+photo.getImageCounts()+")");
        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {

                if(bitmap != null && holder.img != null){
                    holder.img .setImageBitmap(bitmap);
                }
            }
        });

        if(bitmap != null){
            holder.img .setImageBitmap(bitmap);
        }else{
            holder.img .setImageResource(R.drawable.friends_sends_pictures_no);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(mOnItemClickLitener!=null)
                mOnItemClickLitener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
     class MyViewHolder extends RecyclerView.ViewHolder
    {

        MyImageView img;
        TextView tv_count;
        public TextView mTextViewTitle;
        public MyViewHolder(View view)
        {
            super(view);
            img = (MyImageView) view.findViewById(R.id.group_image);
            tv_count= (TextView) view.findViewById(R.id.group_count);
            mTextViewTitle= (TextView) view.findViewById(R.id.group_title);

        }
    }
}
