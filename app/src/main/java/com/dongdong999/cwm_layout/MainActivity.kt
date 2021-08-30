package com.dongdong999.cwm_layout

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.dongdong999.cwm_layout.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val blankFragment by lazy { BlankFragment() }
    private val blankFragment2 by lazy { BlankFragment2() }
    private val blankFragment3 by lazy { BlankFragment3() }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)




        //bottom 초기화
        var bnv_main = findViewById(R.id.bnv_main) as BottomNavigationView
        bnv_main.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.first -> {
                        changeFragment(blankFragment)
                    }
                    R.id.second -> {
                        changeFragment(blankFragment2)
                    }
                    R.id.third -> {
                        changeFragment(blankFragment3)
                    }
                }
                true
            }
            selectedItemId = R . id . first
        }
    }


    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }


}