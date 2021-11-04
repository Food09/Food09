package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment();
        val chatListFragment = ChatListFragment();
        val myPageFragment = MyPageFragment();

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view);

        replaceFragment(homeFragment);

        // Load Firebase User Data by email
        if (!intent.hasExtra("email")){
            return
        }
        val email : String? = intent.getStringExtra("email")
        Log.d("MainActivity email : ", email.toString())



        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    replaceFragment(homeFragment)
                }
                R.id.chatList -> replaceFragment(chatListFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragementContainer, fragment)
                commit()
            }
    }
}