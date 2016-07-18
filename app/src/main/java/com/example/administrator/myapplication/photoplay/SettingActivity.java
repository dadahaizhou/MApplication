package com.example.administrator.myapplication.photoplay;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.photoplay.util.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg_music_path)
    EditText edit_path;
    @BindView(R.id.btn_save_path)
    Button btn_save;
    Context con;
    String bgPath="/PhotoMusic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       initEditData();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path=edit_path.getText().toString();
                if(path!=null&&!path.isEmpty()){
                    bgPath=path;
                    SharePreferenceUtil.setPrefString(con,"bgmusic",bgPath);

                }
                else{
                    Toast.makeText(con,"路径不可为空",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
  void  initEditData(){
     bgPath= SharePreferenceUtil.getPrefString(con,"bgmusic",bgPath);
      edit_path.setText(bgPath);
    }

}
