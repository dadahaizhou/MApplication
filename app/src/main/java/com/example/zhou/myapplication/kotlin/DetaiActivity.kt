package com.example.zhou.myapplication.kotlin

import android.app.Activity
import android.os.Bundle
import com.example.zhou.myapplication.Employer
import com.example.zhou.myapplication.R
import kotlinx.android.synthetic.main.activity_detai.*
import com.example.zhou.myapplication.kotlin.ExtendMethod.*
import org.jetbrains.anko.toast

class DetaiActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detai)
        var emp: Employer? =intent.getSerializableExtra("emp") as Employer
        println("oncreate detaiil $emp")
        tv_detail.text="$emp"
//        this.ShowLog("DetaiActivity>>>>>>>>>>>>>>>>>>")
//         toast1(this ,"aa")
        var content:String="this is  a test"
//        content.ShowStr("str")
    }

}





