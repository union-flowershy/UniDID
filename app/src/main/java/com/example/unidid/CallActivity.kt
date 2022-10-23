package com.example.unidid

//import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner


class CallActivity: AppCompatActivity() {

    lateinit var callNum: EditText
    lateinit var sendBtn: Button
    lateinit var textView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_main)

        callNum = findViewById<View>(R.id.call_id) as EditText
        sendBtn = findViewById<View>(R.id.btn_call) as Button
        textView = findViewById(R.id.textView) as TextView
        val animTransRight: Animation = AnimationUtils.loadAnimation(this, R.anim.aniutils)
        val mImgView = findViewById(R.id.textView) as TextView

        mImgView.startAnimation(animTransRight)

        sendBtn.setOnClickListener { view ->
            val thread = NetworkThread()
            thread.start()
            
        }

        val intent = getIntent()
        val storeName = intent.getStringExtra("storeName")
        val text_name = findViewById<TextView>(R.id.textView)
        text_name.setText(storeName)
    }

    inner class NetworkThread: Thread() {
        @SuppressLint("SuspiciousIndentation")
        override fun run() {
            try {
                val socket: Socket

                //소켓 서버 접속
                //socket = Socket("192.168.10.19", 55555)
                socket = Socket("192.168.1.164", 55555)
                System.out.println("서버 접속 성공")

                // 서버에 보낼 주문번호 전송
                val output = socket.getOutputStream()
                val writer: PrintWriter = PrintWriter(output, true )
                    writer.println(callNum.text.toString())
                    Log.e("전송에 성공했습니다 전송 번호는 = ", callNum.text.toString())
//                val dos = DataOutputStream(output)
//                val orderNum = callNum.text.toString()
//                dos.writeUTF(orderNum)
//                Log.e("전송에 성공했습니다 전송 번호는 = ", orderNum)

                // 서버에서 보낸 메세지 읽는 Thread
//                val input = socket.getInputStream()
//                val dis = DataInputStream(input)

                if(output != null) {
                    Log.e("전송에", "성공했습니다")
                } else {
                    Log.e("전송에", "실패했습니다")
                }

            }catch(e: Exception) {
                e.printStackTrace()
            }
        }


    }

}
