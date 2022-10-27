package com.example.unidid

//import android.R

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.PrintWriter
import java.net.Socket


class CallActivity: AppCompatActivity() {

    lateinit var callNum: EditText
    lateinit var sendBtn: Button
    lateinit var resetBtn: Button
    lateinit var textView: TextView
    lateinit var callMain: ConstraintLayout
    lateinit var type: String


    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_main)

        callNum = findViewById<View>(R.id.call_id) as EditText
        sendBtn = findViewById<View>(R.id.btn_call) as Button
        resetBtn = findViewById(R.id.btn_reset) as Button
        textView = findViewById<View>(R.id.textView) as TextView
        callMain = findViewById<View>(R.id.all_main) as ConstraintLayout
        val animTransRight: Animation = AnimationUtils.loadAnimation(this, R.anim.aniutils)
        val mImgView = findViewById<View>(R.id.textView) as TextView

        mImgView.startAnimation(animTransRight)

        //텍스트 바깥 레이아웃 클릭시 키보드 사라짐
        callMain.setOnTouchListener(OnTouchListener { v, event ->
            hideKeyboard()
            false
        })

        sendBtn.setOnClickListener {
            if (!callNum.text.toString().equals(null) && !callNum.text.toString().equals("") && !callNum.text.toString().equals("null")) {
                val thread = NetworkThread("sendBtn")
                thread.start()
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("전송되었습니다.")
                    .setMessage(callNum.text.toString())
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                    })
                builder.show()
            }else {
                Toast.makeText(this@CallActivity, "주문번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        resetBtn.setOnClickListener {
            val thread: Thread = NetworkThread("resetBtn")
                thread.start()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("초기화")
                  .setMessage("초기화를 진행하였습니다")
                  .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                  })
                builder.show()
        }

        val intent = getIntent()
        val storeName = intent.getStringExtra("storeName")
        val text_name = findViewById<TextView>(R.id.textView)
        text_name.setText(storeName)

    }

    //텍스트 바깥 레이아웃 클릭시 키보드 사라짐
    fun hideKeyboard() {
        val inputManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    inner class NetworkThread(s: String): Thread() {
        val type: String = s
        @SuppressLint("SuspiciousIndentation")
        override fun run() {
            try {
                val socket: Socket

                //소켓 서버 접속
                socket = Socket("192.168.10.19", 55555) // 사무실 IP
//                socket = Socket("192.168.1.164", 55555) // 집 IP
                System.out.println("서버 접속 성공")

                if (type.equals("sendBtn")) {
                    // 서버에 보낼 주문번호 전송
                    val output = socket.getOutputStream()
                    val writer: PrintWriter = PrintWriter(output, true)

                    writer.println(callNum.text.toString())
                    Log.e("전송에 성공했습니다 전송 번호는 = ", callNum.text.toString())
                    callNum.text = null


                        if (output != null) {
                            Log.e("전송에", "성공했습니다")
                        } else {
                            Log.e("전송에", "실패했습니다")
                        }
                } else {
                    val output = socket.getOutputStream()
                    val writer: PrintWriter = PrintWriter(output, true)
                    writer.println("reset")
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
