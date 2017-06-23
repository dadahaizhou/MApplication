package com.example.zhou.myapplication

import java.io.Serializable

class Employer constructor(name: String) :Serializable{
    constructor(name: String, number: String) : this(name) {
        this.number = number
    }

    var name: String? = null
//        get() = field?.toUpperCase()
        set(value) {
            field = "$value"
        }

    var number: String? = null
//        get() = field?.toUpperCase()
//        set(value) {
//            field = "Name:$value"
//        }
    var id: String? = null

    init {
        this.name = name
    }

    override fun toString(): String {
        return "name:$name number:$number id:$id"
    }


}
