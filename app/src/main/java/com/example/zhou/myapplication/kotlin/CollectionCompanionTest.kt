package com.example.zhou.myapplication

/**
 * 静态类
 */
object CollectionCompanionTest  {

    fun testList(){
        var list1=Array(5,{i ->(i*i).toString()})
        val items= listOf("apple", "banana", "kiwi")

        val it= mutableListOf<String>()
        it.addAll(list1)
        it.addAll(items)
        for (index in it.indices) {
            println("item at $index is ${it[index]}")
        }

        for (x in 1..10 step 2) {
            println(x)
        }
        for (x in 9 downTo 0 step 3) {
            println(x)
        }
    }
}
