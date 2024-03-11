package com.restapi.ui.activity.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.text.TextPaint
import com.restapi.ui.base.BaseActivity
import com.restapi.databinding.ActivityMainBinding
import com.restapi.ui.activity.demo.activity.DemoActivity
import com.restapi.ui.activity.demo2.activity.PaginationActivity
import com.restapi.ui.activity.demo3.activity.PostActivity
import com.restapi.ui.activity.task1.activity.FactActivity
import com.restapi.ui.activity.task2.activity.UserListActivity
import com.restapi.ui.activity.task3.activity.UserPostActivity
import java.util.Locale


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setGradientText()
        setClickListener()
    }

    private fun setGradientText() = with(binding) {
        textViewWelcome.text = "Welcome".uppercase(Locale.getDefault())

        val paint: TextPaint = textViewWelcome.paint
        val width = paint.measureText("Welcome")

        val textShader: Shader = LinearGradient(
            0f, 0f, width, textViewWelcome.textSize,
            intArrayOf(
                Color.parseColor("#8446CC"),
                Color.parseColor("#F97C3C"),
                Color.parseColor("#478AEA"),
                Color.parseColor("#FDB54E"),
                Color.parseColor("#64B678")
            ), null, TileMode.CLAMP
        )
        textViewWelcome.paint.setShader(textShader)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setClickListener() = with(binding) {
        buttonApiCallDemo.setOnClickListener {
            moveToNextScreen(this@MainActivity, DemoActivity::class.java)
        }

        buttonApiCallTask1.setOnClickListener {
            moveToNextScreen(this@MainActivity, FactActivity::class.java)
        }
        buttonPostApiCall.setOnClickListener {
            moveToNextScreen(this@MainActivity, PostActivity::class.java)
        }
        buttonApiCallPagination.setOnClickListener {
            moveToNextScreen(this@MainActivity, PaginationActivity::class.java)
        }

        buttonApiCallTask2.setOnClickListener {
            moveToNextScreen(this@MainActivity, UserListActivity::class.java)
        }
        buttonApiCallTask3.setOnClickListener {
            moveToNextScreen(this@MainActivity, UserPostActivity::class.java)
        }
    }
}