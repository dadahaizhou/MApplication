package com.example.zhou.myapplication.kotlin.inter

import com.example.zhou.myapplication.Employer

/**
 * Created by Administrator on 2017/6/20.
 */
interface IGetData {
    fun onDataResult(list:List<Employer>)
}