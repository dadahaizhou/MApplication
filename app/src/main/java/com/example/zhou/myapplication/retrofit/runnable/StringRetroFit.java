package com.example.zhou.myapplication.retrofit.runnable;

import android.util.Log;

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
       // Get_String();
        QueryGET();
    }

    public interface StringClient {
        //方法的返回值是String,需要StringConverter转换器Converter把Response转换为String.
        @GET("/")
        Call<String> getString();
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
                .addConverterFactory(StringConverterFACTORY)
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
    public  void Get_String(){
        String url="http://tieba.baidu.com";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(StringConverterFACTORY)
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
          StringConverter.Factory StringConverterFACTORY=new StringConverter.Factory(){
            @Override
            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[]  annotations, Retrofit retrofit) {
            /*if (type==StringConverter.class)return  new StringConverter();
            else return null;*/
                Log.i("StringRetrofit","responseBodyConverter>>");
                return  new StringConverter();
            }
        };
    public class StringConverter implements Converter<ResponseBody,String>{
        @Override
        public String convert(ResponseBody value) throws IOException {
            //values.string 把服务器上请求的数据，转换成string格式
//        String str=convertStream2String(value.byteStream());
          Log.i("StringRetrofit","StringConverter convert>>"+value);

            return value.string();
        }
        private String convertStream2String(InputStream in) throws IOException {
            //inputStream转换为String 要进行gbk或者utf-8转码，否则乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
//        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder sb=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        }
    }

}
