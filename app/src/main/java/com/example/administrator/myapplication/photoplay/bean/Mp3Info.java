package com.example.administrator.myapplication.photoplay.bean;

/**
 * Created by Administrator on 2016/7/14.
 */
public class Mp3Info {
    private String name;

    private String url;

    public Mp3Info(){

    }
   public Mp3Info(String url){
    this.url=url;
    }

    public String getUrl() {
        return url;
    }
}
