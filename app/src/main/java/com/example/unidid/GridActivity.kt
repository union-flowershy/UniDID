package com.example.unidid

//import android.R

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.PrintWriter
import java.net.Socket


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
        val callBtn: Button = findViewById(R.id.callBtn)
        val clearBtn: Button = findViewById(R.id.clearBtn)


        num0.setOnClickListener(numClickListener)
        num1.setOnClickListener(numClickListener)
        num2.setOnClickListener(numClickListener)
        num3.setOnClickListener(numClickListener)
        num4.setOnClickListener(numClickListener)
        num5.setOnClickListener(numClickListener)
        num6.setOnClickListener(numClickListener)
        num7.setOnClickListener(numClickListener)
        num8.setOnClickListener(numClickListener)
        num9.setOnClickListener(numClickListener)
        back.setOnClickListener(numClickListener)
        left.setOnClickListener(numClickListener)
        right.setOnClickListener(numClickListener)
        callBtn.setOnClickListener(numClickListener)
        clearBtn.setOnClickListener(numClickListener)
    }

   private val numClickListener = View.OnClickListener { view ->

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
            R.id.back -> {
                val deleteText = first_editText.text.toString()
                var delete = deleteText.length.toString()
                if(delete <= 0.toString()) {
                    first_editText.append("")
                } else {
                    delete = deleteText.substring(0, deleteText.length -1)
                    first_editText.text = delete
                    Log.e("else접속 : ", "1회 실행")
                }
            }
            R.id.callBtn -> {
                if (!first_editText.text.toString().equals(null) && !first_editText.text.toString().equals("") && !first_editText.text.toString().equals("null")) {
                    val thread = NetworkThread("callBtn")
                    thread.start()
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("전송되었습니다.")
                        .setMessage(first_editText.text.toString())
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.show()
                } else {
                    Toast.makeText(this@GridActivity, "주문번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.clearBtn -> {
                val thread: Thread = NetworkThread("clearBtn")
                thread.start()
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("초기화")
                    .setMessage("초기화를 진행하였습니다")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                    })
                builder.show()
            }
        }
   }

    inner class NetworkThread(s: String): Thread() {
        private val type: String = s
        @SuppressLint("SuspiciousIndentation")
        override fun run() {
            try {
                //소켓 서버 접속
//                val socket: Socket = Socket("192.168.10.19", 55555) // 사무실 IP
                val socket = Socket("192.168.1.164", 55555) // 집 IP
                println("서버 접속 성공")

                if (type.equals("callBtn")) {
                    // 서버에 보낼 주문번호 전송
                    val output = socket.getOutputStream()
                    val writer: PrintWriter = PrintWriter(output, true)

                    writer.println(first_editText.text.toString())
                    Log.e("전송에 성공했습니다 전송 번호는 = ", first_editText.text.toString())
                    first_editText.text = null

                    if (output != null) {
                        Log.e("전송에", "성공했습니다")
                    } else {
                        Log.e("전송에", "실패했습니다")
                    }
                } else {
                    val output = socket.getOutputStream()
                    val writer: PrintWriter = PrintWriter(output, true)
                    writer.println("clearBtn")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
