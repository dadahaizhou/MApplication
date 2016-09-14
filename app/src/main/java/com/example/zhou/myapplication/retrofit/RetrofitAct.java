package com.example.zhou.myapplication.retrofit;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.zhou.myapplication.R;
import com.example.zhou.myapplication.retrofit.runnable.StringRetroFit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import android.widget.Toast;

public class RetrofitAct extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        executorService.execute(run_git);
        executorService.execute(new StringRetroFit());

    }

    Runnable  run_git=new Runnable() {
        @Override
        public void run() {
            path_GitHub();

        }
    };
    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(@Path("owner") String owner,
                                               @Path("repo") String repo);
    }
     class Contributor {
        //一个pojo类(Plain Ordinary Java Object）简单的Java对象-->相比javaBean更简单. GsonConverter默认的转换器
        String login;
        int contributions;
        @Override
       public String toString(){
               return login+" contributions:"+contributions;
        }
    }
    StringBuffer sb=new StringBuffer();
    public  void path_GitHub(){
        sb.setLength(0);
        String url="https://api.github.com";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //create a damynic proxy
        GitHub create=retrofit.create(GitHub.class);
        /**
         * 访问这个地址返回的是一个JsonArray,JsonArray的每一个元素都有login
         * 和contributions这2个key和其对应的value.提取出来封装进POJO对象中.
         */
        Call<List<Contributor>> call=create.contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                Log.i("RetrofitAct","onResponse>>>>>>>>");
                List<Contributor>list=response.body();
                for (Contributor c:list) {
                    sb.append(c);
                    sb.append("\n");
                    Log.d("RetrofitAct","Contributor:"+c);
                }
                setShowViewContent(true);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Log.i("RetrofitAct","onFailure>>>>>>>>");
                setShowViewContent(false);
                t.printStackTrace();

            }
        });


    }
    void setShowViewContent(boolean isSuc){
        mProgressBar.setVisibility(View.GONE);
        if(isSuc)
        mTvContent.setText(sb.toString());
        else{
            Toast.makeText(RetrofitAct.this,"failure",Toast.LENGTH_SHORT).show();
        }
    }

}
