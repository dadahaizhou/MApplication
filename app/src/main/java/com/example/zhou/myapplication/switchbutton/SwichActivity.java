package com.example.zhou.myapplication.switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.zhou.myapplication.R;
import com.example.zhou.myapplication.switchbutton.view.SwitchButton;

public class SwichActivity extends AppCompatActivity {

    private ToggleButton mTogBtn;
    SwitchButton switchButton;
     String TAG="SwitchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swich);
        initView();
    }

    private void initView() {
        mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
        switchButton= (SwitchButton) findViewById(R.id.m_switchview);
        switchButton.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchButton view) {
                view.toggleSwitch(true);
                Log.i(TAG,"toggleToOn>>>>");
            }

            @Override
            public void toggleToOff(SwitchButton view) {
                view.toggleSwitch(false);
                Log.i(TAG,"toggleToOff>>>>");
            }
        });
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                }else{
                    //未选中
                }
            }
        });// 添加监听事件

    }
}
