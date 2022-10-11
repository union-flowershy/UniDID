//package com.example.unidid
//
//import android.content.Intent
//import android.os.AsyncTask
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat.startActivity
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStreamReader
//import java.io.OutputStreamWriter
//import java.net.HttpURLConnection
//import java.net.MalformedURLException
//import java.net.URL
//
//public open class MainActivity : AppCompatActivity() {
//
//    lateinit var userId: EditText
//    lateinit var userPwd: EditText
//    lateinit var loginBtn: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        userId = findViewById(R.id.edit_id)
//        userPwd = findViewById(R.id.edit_pwd)
//        loginBtn = findViewById(R.id.btn_login)
//        loginBtn.setOnClickListener(btnListener)
//
//    }
//
//    open class CustomTask : AsyncTask<String, Void, String>() {
//        @Deprecated("Deprecated in Java")
//        override fun doInBackground(vararg strings: String?): String {
//            lateinit var sendMsg: String
//            lateinit var receiveMsg: String
//
//            try {
//                var str: String?
//                val url = URL("http://192.168.10.17: 8080/")
//                val conn = url.openConnection() as HttpURLConnection
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//                conn.requestMethod = "POST"
//                val osw = OutputStreamWriter(conn.outputStream)
//                sendMsg = "id = " + strings[0] + "&pwd = " + strings[1] + "&type = " + strings[2]
//                osw.write(sendMsg)
//                osw.flush()
//
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
//
//        }
//    }
//
//    var btnListener = View.OnClickListener { view ->
//            when(view.id) {
//                R.id.btn_login -> {
//                    val loginid = MainActivity().userId.text.toString()
//                    val loginpw : String = MainActivity().userPwd.text.toString()
//
//                    try {
//                        val result : String = CustomTask().execute(loginid, loginpw, "login").get()
//                        if(result.equals("true")) {
//                            Toast.makeText(MainActivity(), "로그인", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this, test123::class.java)
//                            startActivity(intent)
//                        }
//                    }catch (e: Exception) {}
//
//
//
//                }
//
//            }
//        }
//}