package com.example.zhou.myapplication.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhou.myapplication.Employer
import com.example.zhou.myapplication.R
import kotlinx.android.synthetic.main.item_view.view.*

/**
 * Created by Administrator on 2017/6/20.
 */
class EmployerAdapter(var data: List<Employer>,var context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var holder0:ViewHolder0= holder as ViewHolder0
        holder0.bind(getItem(position))
        holder0.bindItemClick(onItemClick!!,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder0(LayoutInflater.from(context).inflate(R.layout.item_view,null))
    }

    override fun getItemCount(): Int {
        return data?.size

    }
     fun getItem( pos:Int): Employer {
        return data[pos]

    }
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemView0:View=itemView
        fun bind(item: Employer){
            itemView0.tv_name.text = item.name
            itemView0.tv_num.text=item.number
        }
        fun bindItemClick(onItemClick: IOnItemClick,pos :Int){
           itemView0.setOnClickListener {
               println("bindItemClick $pos")
               onItemClick.onItemClick(pos)
           }
        }
    }
    var onItemClick:IOnItemClick?=null
     interface IOnItemClick{
        fun onItemClick(pos:Int)
    }
}