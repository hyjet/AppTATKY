package com.kevinli5506.appta

import com.kevinli5506.appta.Model.RecycleItem

object RecycleItemData {
    private val type:ArrayList<String> = arrayListOf("Kaca","Besi","Kertas")
    private val price: ArrayList<Double> = arrayListOf(10000.0,15000.0,500.0)


    val listRecycleItem:ArrayList<RecycleItem>
    get() {
        val list:ArrayList<RecycleItem> = arrayListOf()
        for (i in (0 until type.size)){
            val recycleItem : RecycleItem =
                RecycleItem(
                    type[i],
                    price[i]
                )
            list.add(recycleItem)
        }
        return list
    }
}