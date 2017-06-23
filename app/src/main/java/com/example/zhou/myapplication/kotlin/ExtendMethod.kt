package com.example.zhou.myapplication.kotlin

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by Administrator on 2017/6/22.
 * 扩展函数测试  未知原因不起作用//TODO
 */
 open class ExtendMethod {
    public fun Activity.ShowLog(s: String){
        Log.i("Activity","showTest $s")
    }
    public fun String.ShowStr(s: String){
        Log.i("String","showTest $s")
    }
    fun android.app.Activity.toast1(context: Context,s:CharSequence){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show()
    }
}