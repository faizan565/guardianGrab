package com.example.guardiangrab

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class homepage : AppCompatActivity() {
    public lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val buttonLogoout=findViewById<Button>(R.id.logout)
        val button=findViewById<Button>(R.id.call)
        button.setOnClickListener {

            val intent1 = Intent(this, call::class.java)
            startActivity(intent1)
        }

        val button1=findViewById<Button>(R.id.sms)
        button1.setOnClickListener {

            val intent2 = Intent(this, sms::class.java)
            startActivity(intent2)
        }

        val button2=findViewById<Button>(R.id.location)
        button2.setOnClickListener {

            val intent3 = Intent(this, location::class.java)
            startActivity(intent3)
        }
        buttonLogoout.setOnClickListener {
            sp = getSharedPreferences("login", Context.MODE_PRIVATE)
            sp.edit().putBoolean("logged", false).apply()
            sp.edit().putString("worker", "").apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}