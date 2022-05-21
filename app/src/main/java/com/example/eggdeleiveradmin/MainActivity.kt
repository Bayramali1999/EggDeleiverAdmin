package com.example.eggdeleiveradmin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation

    //private lateinit var binding: ResultProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_main)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)

        splash_iv.animation = topAnim
        textView2.animation = bottomAnim


        Handler(Looper.myLooper()!!)
            .postDelayed({
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }, 2000L)

    }
}