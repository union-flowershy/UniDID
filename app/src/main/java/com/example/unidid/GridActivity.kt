package com.example.unidid

//import android.R

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import java.io.PrintWriter
import java.net.Socket


class GridActivity: AppCompatActivity() {

    private lateinit var first_editText: TextView
    private lateinit var gridView: GridView
//    private val numberWord = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "←", "0", "호출\n삭제", "왼쪽", "오른쪽", "호출")
//    private val numberImage = arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six)
    private var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    lateinit var callBtn: Button


    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number_main2)

        // 환경설정 메뉴바 생성
        setSupportActionBar(main_layout_toolbar)    // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.list_large)  // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false)     // 툴바에 타이틀 안보이게 설정
//        main_navigationView?.setNavigationItemSelectedListener(this)

        first_editText = findViewById<TextView>(R.id.first_editText)
        callBtn = findViewById<View>(R.id.callBtn) as Button

        setButton()

        auth = Firebase.auth //파이어베이스 가입
        firestore = FirebaseFirestore.getInstance() //파이어베이스 스토어 초기화
    }

    // 액션바 생성
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {  // 메뉴버튼
                main_drawer_layout.openDrawer(GravityCompat.START)  // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 액션바 닫기
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawers()
            //테스트용
            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }


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
                    val resultDTO = ResultDTO()
                        resultDTO.uid = auth?.currentUser?.uid
                        resultDTO.callNumber = first_editText.text.toString()
                        resultDTO.timestamp = System.currentTimeMillis()
                        firestore?.collection(auth!!.currentUser!!.uid)?.document()?.set(resultDTO)
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("전송되었습니다.")
                        .setMessage(first_editText.text.toString())
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.show()
                    first_editText.text = null
                } else {
                    Toast.makeText(this@GridActivity, "주문번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.clearBtn -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("초기화")
                        .setMessage("초기화를 진행하였습니다")
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.show()
                    first_editText.text = null
            }
        }
   }

}