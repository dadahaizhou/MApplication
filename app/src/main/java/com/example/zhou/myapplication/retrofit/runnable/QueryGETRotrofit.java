package com.example.zhou.myapplication.retrofit.runnable;

import android.util.Log;

import com.example.zhou.myapplication.retrofit.StringConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/9/14.
 */
public class QueryGETRotrofit implements  Runnable {
    @Override
    public void run() {
        QueryGET();
    }

    public interface QueryGET {
        @GET("/sheet")
        Call<String> getString(@Query("name") String name,
                               @Query("age")int age,
                               @QueryMap(encoded=true)
                               Map<String, String> filters
        );
    }
    public  void QueryGET(){
        String url="http://tieba.baidu.com";
        Map<String, String> map=new HashMap<>();
        map.put("gender","male");
        map.put("address","sz");
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(StringConverter.FACTORY)
                .build();
        QueryGET queryGET=retrofit.create(QueryGET.class);
        Call<String> call=queryGET.getString("laiqurufeng", 22, map);
        try {
            Log.i("StringRetrofit","QueryGET>>0000");
            Response<String> str=call.execute();
            Log.i("StringRetrofit","QueryGET>>"+str.body());
        } catch (IOException e) {
            Log.i("StringRetrofit","QueryGET>>1111");
            e.printStackTrace();
        }
    }
}
