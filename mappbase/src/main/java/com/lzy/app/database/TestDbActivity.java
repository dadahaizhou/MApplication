package com.lzy.app.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.app.R;
import com.lzy.app.base.BaseActivity;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class TestDbActivity extends BaseActivity implements  View.OnClickListener{
    @Bind(R.id.button1)
    Button insert;
    @Bind(R.id.button2)
    Button delete;
    @Bind(R.id.button3)
    Button select;
    @Bind(R.id.button4)
    Button update;

    @Bind(R.id.textView3)
    TextView contentTv;

    private MDBHelper testDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        testDBHelper = MDBHelper.getInstance(getApplicationContext());
        insert.setOnClickListener(this);
        select.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button3:
                List<Map> list = testDBHelper.queryListMap("select * from user",null);
                contentTv.setText(String.valueOf(list));
                break;
            case R.id.button1:
                testDBHelper.insert("user",new String[]{"name","gender","age"},new Object[]{"qiangyu","male",23});
                break;
            case R.id.button4:
                testDBHelper.update("user",new String[]{"name","gender","age"},new Object[]{"yangqiangyu","male",24},
                        new String[]{"name"},new String[]{"qiangyu"});
                break;
            case R.id.button2:
                testDBHelper.delete("user",
                        new String[]{"name"},new String[]{"qiangyu"});
                break;
        }
    }
}
