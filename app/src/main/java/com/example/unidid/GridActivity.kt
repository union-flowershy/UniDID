package com.example.unidid

//import android.R

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout



class GridActivity: AppCompatActivity() {

    private lateinit var first_editText: TextView
    private lateinit var gridView: GridView
    private val numberWord = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "←", "0", "호출\n삭제", "왼쪽", "오른쪽", "호출")
    private val numberImage = arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six)



    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number_main2)

        first_editText = findViewById<TextView>(R.id.first_editText)

        setButton()
//        gridView = findViewById(R.id.gridView)
//        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberWord, numberImage)
//        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberWord)
//        val numberAdapter: NumberAdapter = NumberAdapter(this@GridActivity, numberImage)

//        gridView.adapter = numberAdapter

//        gridView.setOnItemClickListener(AdapterView.OnItemClickListener() {
//            override fun onItemClick(adapterView: AdapterView?, view: View, position: Int) {
//            -> {
//            }
//            })
//        }
    }

    fun setButton() {
        val num0: Button = findViewById(R.id.num0)
        val num1: Button = findViewById(R.id.num1)
        val num2: Button = findViewById(R.id.num2)
        val num3: Button = findViewById(R.id.num3)
        val num4: Button = findViewById(R.id.num4)
        val num5: Button = findViewById(R.id.num5)
        val num6: Button = findViewById(R.id.num6)
        val num7: Button = findViewById(R.id.num7)
        val num8: Button = findViewById(R.id.num8)
        val num9: Button = findViewById(R.id.num9)
        val back: Button = findViewById(R.id.back)
        val left: Button = findViewById(R.id.left)
        val right: Button = findViewById(R.id.right)

        num0.setOnClickListener(numClickListener);
        num1.setOnClickListener(numClickListener);
        num2.setOnClickListener(numClickListener);
        num3.setOnClickListener(numClickListener);
        num4.setOnClickListener(numClickListener);
        num5.setOnClickListener(numClickListener);
        num6.setOnClickListener(numClickListener);
        num7.setOnClickListener(numClickListener);
        num8.setOnClickListener(numClickListener);
        num9.setOnClickListener(numClickListener);
        back.setOnClickListener(numClickListener);
        left.setOnClickListener(numClickListener);
        right.setOnClickListener(numClickListener);
    }

   val numClickListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.num0 -> { first_editText.append("0") }
            R.id.num1 -> { first_editText.append("1") }
            R.id.num2 -> { first_editText.append("2") }
            R.id.num3 -> { first_editText.append("3") }
            R.id.num4 -> { first_editText.append("4") }
            R.id.num5 -> { first_editText.append("5") }
            R.id.num6 -> { first_editText.append("6") }
            R.id.num7 -> { first_editText.append("7") }
            R.id.num8 -> { first_editText.append("8") }
            R.id.num9 -> { first_editText.append("9") }

        }


    }



}
