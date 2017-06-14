package com.example.zhou.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MCircleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcircle_view);
        testGit();
    }
    void testGit(){
        String tip="testGit";
        Log.i("git","tip:"+tip);
    }
}
