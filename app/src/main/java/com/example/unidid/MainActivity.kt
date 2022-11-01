package com.example.unidid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.internal.ViewUtils.hideKeyboard
import java.io.*
import java.net.*
import java.lang.*

class MainActivity : AppCompatActivity() {

    lateinit var userId: EditText
    lateinit var userPwd: EditText
    lateinit var loginBtn: Button
    lateinit var activityMain: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = findViewById<View>(R.id.edit_id) as EditText
        userPwd = findViewById<View>(R.id.edit_pwd) as EditText
        loginBtn = findViewById<View>(R.id.btn_login) as Button
        activityMain = findViewById<View>(R.id.activityMain) as ConstraintLayout
        loginBtn.setOnClickListener(btnListener)

        Log.e("onCreate 실행", "정상 실행")


        //텍스트 바깥 레이아웃 클릭시 키보드 사라짐
        activityMain.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })
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

    class CustomTask : AsyncTask<String?, Void?, String?>() {
        var sendMsg: String? = null
        var receiveMsg: String? = null

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg strings: String?): String {
            try {
                var str: String?
                val url = URL("http://192.168.10.19:8080/exex/data.jsp")  // 사무실 IP
//                val url = URL("http://192.168.1.164:8080/exex/data.jsp")  // 집 IP
                val conn = url.openConnection() as HttpURLConnection
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                conn.requestMethod = "POST"
                val osw = OutputStreamWriter(conn.outputStream)
                sendMsg = "userID=" + strings[0] + "&userPW=" + strings[1] + "&type=" + strings[2]
                osw.write(sendMsg)
                osw.flush()
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val tmp = InputStreamReader(conn.inputStream, "UTF-8")
                    val reader = BufferedReader(tmp)
                    val buffer = StringBuffer()
                    str = reader.readLine()
                    while (reader.readLine().also { str = it } != null) {
                        buffer.append(str)
                    }
                    receiveMsg = buffer.toString()
                    Log.e("receiveMsg ", buffer.toString())
                } else {
                    Log.i("통신 결과", conn.responseCode.toString() + "에러")
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return receiveMsg!!
        }
    }

    @SuppressLint("SuspiciousIndentation")
    var btnListener = View.OnClickListener { view ->
            when(view.id) {
                R.id.btn_login -> {
                    val loginid = userId.text.toString()
                    val loginpw = userPwd.text.toString()
                    Log.e("에러코드 ID 확인 = ", loginid)
                    Log.e("에러코드 ID 확인 = ", loginpw)
                    try {
                        val result: String? = CustomTask().execute(loginid, loginpw, "login").get()
                            if (result == "true") {
                                Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, GridActivity::class.java)
                                intent.putExtra("storeName", loginid)
                                startActivity(intent)
                                finish()
                            } else if (result == "false") {
                                Toast.makeText(this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                userId.setText("")
                                userPwd.setText("")
                            } else if (result == "noId") {
                                Log.e("아이디 없음", "정상 실행")
                            } else {
                                Log.e("result 실패", result.toBoolean().toString())
                            }
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
}