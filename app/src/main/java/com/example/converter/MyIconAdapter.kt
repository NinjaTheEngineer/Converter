package com.example.converter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyIconAdapter : RecyclerView.Adapter<MyIconAdapter.Companion.Holder>{

    companion object{
        class Holder: RecyclerView.ViewHolder{
            lateinit var tvName : TextView
            lateinit var img : ImageView
            lateinit var tvSize : TextView

            constructor(rv: View) : super(rv){
                tvName = rv.findViewById(R.id.tvQuantity)
                img = rv.findViewById(R.id.ivIcon)
                tvSize = rv.findViewById(R.id.tvSize)
            }
        }
    }

    var list: MutableList<IconAmount> = mutableListOf()

    lateinit var context: Context

    constructor(list: MutableList<IconAmount>, context: Context) : super() {
        this.list = list
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        var rv: View
        var holder: Holder
        rv = LayoutInflater.from(parent.context).inflate(R.layout.icon_display, parent, false)
        holder = Holder(rv)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var icon: IconAmount
        icon = list.get(position)
        holder.tvSize.setText("${icon.size}")
        holder.tvName.setText("${icon.name}(${icon.amount})")
        val params = LinearLayout.LayoutParams(icon.dim * 2, icon.dim * 2)
        holder.img.layoutParams = params
        holder.img.setImageResource(icon.image)
    }

}