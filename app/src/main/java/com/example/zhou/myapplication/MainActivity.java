package com.example.zhou.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhou.RxJava1.MainRxActivity;
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
    public static boolean isForeGround;
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
        setIsForeGround(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIsForeGround(true);
    }

    @Override
    protected void onDestroy() {
        setIsForeGround(false);
        super.onDestroy();
    }

    void directJump(){
        //startActivity(new Intent(this, MainRxActivity.class));
        //finish();
        Log.i("MainActivity","app base info>>>>>>>>>>>>>");
        Log.i("MainActivity","this is tinker patch >>>");
        addPatchTest();
    }

    public static void setIsForeGround(boolean isFroeGraound) {
        MainActivity.isForeGround = isFroeGraound;
    }
    void addPatchTest(){
        startActivity(new Intent(this, MainRxActivity.class));
        finish();
    }

}
