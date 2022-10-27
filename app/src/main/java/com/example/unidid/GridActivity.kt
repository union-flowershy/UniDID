package com.example.unidid

//import android.R

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout



class GridActivity: AppCompatActivity() {

    private lateinit var gridView: GridView
    private val numberWord = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "←", "0", "호출\n삭제", "왼쪽", "오른쪽", "호출")
    private val numberImage = arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six)


    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grid_call)

        gridView = findViewById(R.id.gridView)
//        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberWord, numberImage)
//        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberWord)
        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberImage)

        gridView.adapter = numberAdapter

//        gridView.setOnItemClickListener(AdapterView.OnItemClickListener() {
//            override fun onItemClick(adapterView: AdapterView?, view: View, position: Int) {
//            -> {
//            }
//            })
//        }
    }
}
