package com.example.zhou.myapplication.retrofit.runnable;

import android.util.Log;

import com.example.zhou.myapplication.retrofit.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StringRetroFit implements  Runnable{
    @Override
    public void run() {
        Log.i("StringRetrofit","run>>>>>>>>>");
        Get_String();
    }

    public interface StringClient {
        //方法的返回值是String,需要StringConverter转换器Converter把Response转换为String.
        @GET("/")
        Call<String> getString();
    }

    public  void Get_String(){
        String url="http://tieba.baidu.com";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(StringConverter.FACTORY)
                .build();
        final StringClient strClient=retrofit.create(StringClient.class);
        Call<String>call=strClient.getString();
        try {
            Response<String> str=call.execute();
            Log.i("StringRetrofit","strClient>>"+str.body());
//            System.out.print(str.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
