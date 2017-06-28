package com.example.zhou.myapplication.retrofit.module;

/**
 * Created by Administrator on 2017/6/27.
 */

public class Contributor {
    //一个pojo类(Plain Ordinary Java Object）简单的Java对象-->相比javaBean更简单. GsonConverter默认的转换器
    String login;
    int contributions;

    @Override
    public String toString() {
        return login + " contributions:" + contributions;
    }
}
