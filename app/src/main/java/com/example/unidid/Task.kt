package com.example.unidid

import android.hardware.biometrics.BiometricManager.Strings
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class Task : AsyncTask<String, Void, String>() {

    lateinit var sendMsg : String
    lateinit var receiveMsg : String


    var serverip : String = "http://" + ip + "/ex/list.jsp"

    fun Task(sendmsg: String?) {
        sendMsg = sendmsg!!
    }

    companion object {
        var ip = "192.168.10.19" //자신의 IP번호
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg strings: String?): String {

        try {
            var str: String?
            val url = URL(serverip)
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.requestMethod = "POST"
            val osw = OutputStreamWriter(conn.outputStream)
            if (sendMsg == "vision_write") {
                sendMsg = "vision_write=" + strings.get(0).toString() + "&type=" + strings.get(1)
            } else if (sendMsg == "vision_list") {
                sendMsg = "&type=" + strings.get(0)
            }
            osw.write(sendMsg)
            osw.flush()
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                val tmp = InputStreamReader(conn.inputStream, "UTF-8")
                val reader = BufferedReader(tmp)
                val buffer = StringBuffer()
                while (reader.readLine().also { str = it } != null) {
                    buffer.append(str)
                }
                receiveMsg = buffer.toString()
            } else {
                Log.i("통신 결과", conn.responseCode.toString() + "에러")
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return receiveMsg

    }


}