package com.dongdong999.cwm_layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin : Button = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            var intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}