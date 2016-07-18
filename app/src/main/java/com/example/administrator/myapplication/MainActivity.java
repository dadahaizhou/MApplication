package com.example.administrator.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.myapplication.photoplay.MPhotoListActivity;
import com.example.administrator.myapplication.photoplay.PlayPhotoActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
   @BindView(R.id.button0)
    Button btn_recycle;
    @BindView(R.id.button2)
    Button btn_largeimg;

    @BindView(R.id.btn_playphoto)
    Button btn_playphoto;
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
                startActivity(new Intent(MainActivity.this,LargeBitmapActivity.class));
            }
        });
    }


}
