package com.example.zhou.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.zhou.myapplication.kotlin.adapter.EmployerAdapter
import com.example.zhou.myapplication.kotlin.inter.IGetData
import com.example.zhou.myapplication.kotlin.present.EmplorPresent
import kotlinx.android.synthetic.main.activity_kt_main.*

class MainKotlinActivity : AppCompatActivity() ,IGetData{
    override fun onDataResult(list: List<Employer>) {
        dataList0 = list
//        var list0: List<Employer>? = dataList0
//        list0?.let {
//            for (emp in list0) {
//                Log.i(tag, "onDataResult emp:$emp")
//            }
//        }
        adapter= EmployerAdapter(dataList0,this)
        id_recyclerView.layoutManager=layoutManager
        id_recyclerView.adapter=adapter
    }
    var adapter:EmployerAdapter?=null
    var layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(this)
    var a = 0
    var base: Int = 100
    val tag: String = MainKotlinActivity::class.simpleName!!
    var dataList0: List<Employer> = listOf()
    var collectionTest = CollectionTest()
    var present:EmplorPresent= EmplorPresent(this)
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
        tv_result.text = "result:$result"
        testWhen()
        testBaseDataType()
        present.getEmployerList(collectionTest)

    }

    fun testWhen(): Int {
        when (a) {
            1 -> {
                Log.i(tag, "this is 1")
            }
            in 1..5 -> {
                Log.i(tag, "a in 1-5")
            }
            else -> {
                Log.i(tag, "a>5")
            }
        }
        return 1
    }

    fun testBaseDataType() {
        val a: Int = 10000
        println(a === a) // 输出“true”
        val boxedA: Int? = a
        val A: Int = a
        println(boxedA is Int)
        println(A is Int)
        println(boxedA === A)
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA) // ！！！输出“false”！！！
    }

}
