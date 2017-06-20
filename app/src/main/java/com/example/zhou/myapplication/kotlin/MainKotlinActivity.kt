package com.example.zhou.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_kt_main.*

class MainKotlinActivity : AppCompatActivity() {
    var a = 0
    var base: Int = 100
    val tag:String= MainKotlinActivity::class.simpleName!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_main)
        btn_do.setOnClickListener {
            action()
        }
    }

    fun action(): Unit {
        a++
        val result = a + base
        tv_result.text="result:$result"
//         Log.i(tag,"action>>>>")
        testWhen()
        testBaseDataType()
    }

    fun testWhen():Int{
        when(a){
            1 ->{
                Log.i(tag,"this is 1")
                testList()
            }
             in 1..5->{
                 Log.i(tag,"a in 1-5")
             }
             else->{
                 Log.i(tag,"a>5")
             }
        }
      return 1
    }
    fun testBaseDataType(){
        val a: Int = 10000
        println(a === a) // 输出“true”
        val boxedA: Int? = 10000
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA) // ！！！输出“false”！！！
    }
    fun testList(){
        var list1=Array(5,{i ->(i*i).toString()})
        for(c in list1){
            println(c)
        }
    }
}
