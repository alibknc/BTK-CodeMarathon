package com.alibknc.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibknc.wallet.views.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splash = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
        splash.start()
    }
}