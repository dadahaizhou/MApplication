package com.example.zhou.myapplication.retrofit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StringConverter implements Converter<ResponseBody, String> {
    public final static StringConverter.Factory FACTORY = new StringConverter.Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            /*if (type==StringConverter.class)return  new StringConverter();
            else return null;*/
            Log.i("StringRetrofit", "responseBodyConverter>>");
            return new StringConverter();
        }
    };

    @Override
    public String convert(ResponseBody value) throws IOException {
        //values.string 把服务器上请求的数据，转换成string格式
//        String str=convertStream2String(value.byteStream());
        Log.i("StringRetrofit", "StringConverter convert>>" + value);

        return value.string();
    }

    private String convertStream2String(InputStream in) throws IOException {
        //inputStream转换为String 要进行gbk或者utf-8转码，否则乱码
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
//        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
}

