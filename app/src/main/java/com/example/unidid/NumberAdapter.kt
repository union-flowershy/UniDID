package com.example.unidid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NumberAdapter() : BaseAdapter() {

   private var context: Context? = null
   private var arrNumberWord: ArrayList<String>? = null
   private var arrNumberImage: ArrayList<Int>? = null
   private var inflater: LayoutInflater? = null


//    constructor(context: Context, arrNumberWord: ArrayList<String>, arrNumberImage: ArrayList<Int>) : this() {
//        this.context = context
//        this.arrNumberWord = arrNumberWord
//        this.arrNumberImage = arrNumberImage
//    }

//    constructor(context: Context, arrNumberWord: ArrayList<String>) : this() {
//        this.context = context
//        this.arrNumberWord = arrNumberWord
//    }

        constructor(context: Context, arrNumberImage: ArrayList<Int>) : this() {
        this.context = context
        this.arrNumberImage = arrNumberImage
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {

        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view = inflater!!.inflate(R.layout.number_main, null)

        val numberImage: ImageView = view.findViewById(R.id.numberImage) as ImageView
//        val numberWord: TextView = view.findViewById(R.id.numberText) as TextView

        numberImage.setImageResource(arrNumberImage!![position])
//        numberWord.text = arrNumberWord!![position]

        return view
    }


    override fun getCount(): Int {
        return arrNumberImage!!.size
    }

    override fun getItem(position: Int): Any {
        return arrNumberImage!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }



}
