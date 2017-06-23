package com.example.zhou.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhou.myapplication.animation.AnimationActivity;
import com.example.zhou.myapplication.photoplay.MPhotoListActivity;
import com.example.zhou.myapplication.retrofit.RetrofitAct;
import com.example.zhou.myapplication.switchbutton.SwichActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
   @BindView(R.id.button0)
    Button btn_recycle;

    @BindView(R.id.button2)
    Button btn_largeimg;

    @BindView(R.id.btn_playphoto)
    Button btn_playphoto;

    @BindView(R.id.btn_retrofit)
    Button btn_retrofit;
    @BindView(R.id.btn_animation)
    Button btn_animation;
    @BindView(R.id.main_imageview)
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
        btn_playphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MPhotoListActivity.class));
            }
        });
       // Picasso.with(this).load(MConstant.imgUrl).into(img);
        btn_largeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SwichActivity.class));
            }
        });
        btn_retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RetrofitAct.class));
            }
        });
        btn_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
            }
        });
        directJump();
    }
    void directJump(){
        startActivity(new Intent(this, MainKotlinActivity.class));
        finish();
    }

}
