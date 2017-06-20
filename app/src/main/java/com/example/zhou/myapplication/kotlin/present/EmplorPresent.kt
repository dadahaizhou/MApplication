package com.example.zhou.myapplication.kotlin.present

import com.example.zhou.myapplication.CollectionTest
import com.example.zhou.myapplication.kotlin.inter.IGetData

/**
 * Created by Administrator on 2017/6/20.
 */
class EmplorPresent(var getData:IGetData) {
     fun getEmployerList(coll : CollectionTest){
         getData?.onDataResult( coll.getEmployer())
    }

}