package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment();
        val chatListFragment = ChatListFragment();
        val myPageFragment = MyPageFragment();

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view);



        // Load Firebase User Data by email
        if (!intent.hasExtra("email")){
            return
        }
        val email : String? = intent.getStringExtra("email")
        Log.d("MainActivity email : ", email.toString())

        val userRef : DatabaseReference = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        val userQuery : Query = userRef.orderByChild("email")
        Log.d( "MainActivity", userQuery.toString())

        userQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (user in snapshot.children){
                    val userEmail : String = user.child("email").value.toString()
                    Log.d( "MainActivity", user.child("email").value.toString())
                    if (userEmail != email){
                        continue
                    }

//                    Log.d( "MainActivity", user.child("nickName").value.toString())
//                    Log.d( "MainActivity", user.child("profile").value.toString())

                    val userNickName : String = user.child("nickName").value.toString()
                    val userProfile : String = user.child("profile").value.toString()
                    val dateTime : String = user.child("dateTime").value.toString()
                    val userKey : String = user.child("userKey").value.toString()
                    val user : UserModel = UserModel(userKey, userEmail, userNickName, userProfile, dateTime)

                    val bundle : Bundle = Bundle()
                    bundle.putSerializable("userInfo", user)
                    homeFragment.setArguments(bundle)
                    chatListFragment.setArguments(bundle)
                    myPageFragment.setArguments(bundle)

                    replaceFragment(homeFragment);

                    bottomNavigationView.setOnNavigationItemSelectedListener {
                        when(it.itemId){
                            R.id.home -> replaceFragment(homeFragment)
                            R.id.chatList -> replaceFragment(chatListFragment)
                            R.id.myPage -> replaceFragment(myPageFragment)
                        }
                        true
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "Failed to read User Data")
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragementContainer, fragment)
                commit()
            }
    }
}