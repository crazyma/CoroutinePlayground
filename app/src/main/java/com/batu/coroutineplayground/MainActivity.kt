package com.batu.coroutineplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batu.coroutineplayground.ui.main.MainFragment
import com.batu.coroutineplayground.ui.second.SecondFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SecondFragment.newInstance())
                .commitNow()
        }
    }
}