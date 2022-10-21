//package com.example.unidid
//
//import android.app.Activity
//import com.example.unidid.MainActivity
//import android.widget.EditText
//import android.widget.Button
//import android.os.Bundle
//import com.example.unidid.R
//import android.view.View
//import android.os.AsyncTask
//import java.lang.Void
//import java.net.URL
//import java.net.HttpURLConnection
//import java.io.OutputStreamWriter
//import java.io.InputStreamReader
//import java.io.BufferedReader
//import java.lang.StringBuffera
//import android.util.Log
//import java.net.MalformedURLException
//import java.io.IOException
//import android.widget.Toast
//import android.content.Intent
//import java.lang.Exception
//
//class test123 : Activity(), MainActivity {
//    override var userId: EditText? = null
//    override var userPwd: EditText? = null
//    override var loginBtn: Button? = null
//    var joinBtn: Button? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        userId = findViewById<View>(R.id.userId) as EditText
//        userPwd = findViewById<View>(R.id.userPwd) as EditText
//        loginBtn = findViewById<View>(R.id.loginBtn) as Button
//        joinBtn = findViewById<View>(R.id.joinBtn) as Button
//        loginBtn!!.setOnClickListener(btnListener)
//        joinBtn!!.setOnClickListener(btnListener)
//    }
//
//    internal inner class CustomTask : AsyncTask<String?, Void?, String?>() {
//        private var sendMsg: String? = null
//        private var receiveMsg: String? = null
//        protected override fun doInBackground(vararg strings: String): String? {
//            try {
//                var str: String?
//                val url = URL("http://192.168.10.248:8080/sendDataToAndroid/data.jsp")
//                val conn = url.openConnection() as HttpURLConnection
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//                conn.requestMethod = "POST"
//                val osw = OutputStreamWriter(conn.outputStream)
//                sendMsg = "id=" + strings[0] + "&pwd=" + strings[1] + "&type=" + strings[2]
//                osw.write(sendMsg)
//                osw.flush()
//                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
//                    val tmp = InputStreamReader(conn.inputStream, "UTF-8")
//                    val reader = BufferedReader(tmp)
//                    val buffer = StringBuffer()
//                    while (reader.readLine().also { str = it } != null) {
//                        buffer.append(str)
//                    }
//                    receiveMsg = buffer.toString()
//                } else {
//                    Log.i("통신 결과", conn.responseCode.toString() + "에러")
//                }
//            } catch (e: MalformedURLException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            return receiveMsg
//        }
//
//        override fun doInBackground(vararg p0: String?): String? {
//            TODO("Not yet implemented")
//        }
//    }
//
//    var btnListener = View.OnClickListener { view ->
//        when (view.id) {
//            R.id.btn_login -> {
//                val loginid = userId!!.text.toString()
//                val loginpwd = userPwd!!.text.toString()
//                try {
//                    val result: String? = CustomTask().execute(loginid, loginpwd, "login").get()
//                    if (result == "true") {
//                        Toast.makeText(this@test123, "로그인", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this@test123, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    } else if (result == "false") {
//                        Toast.makeText(this@test123, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show()
//                        userId!!.setText("")
//                        userPwd!!.setText("")
//                    } else if (result == "noId") {
//                        Toast.makeText(this@test123, "존재하지 않는 아이디", Toast.LENGTH_SHORT).show()
//                        userId!!.setText("")
//                        userPwd!!.setText("")
//                    }
//                } catch (e: Exception) {
//                }
//            }
//            R.id.joinBtn -> {
//                val joinid = userId!!.text.toString()
//                val joinpwd = userPwd!!.text.toString()
//                try {
//                    val result: String = CustomTask().execute(joinid, joinpwd, "join").get()
//                    if (result == "id") {
//                        Toast.makeText(this@test123, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
//                        userId!!.setText("")
//                        userPwd!!.setText("")
//                    } else if (result == "ok") {
//                        userId!!.setText("")
//                        userPwd!!.setText("")
//                        Toast.makeText(this@test123, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show()
//                    }
//                } catch (e: Exception) {
//                }
//            }
//        }
//    }
//}