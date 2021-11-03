package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Activity_Sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

        var userID = findViewById<EditText>(R.id.sign_up_userID)
        var userPW = findViewById<EditText>(R.id.sign_up_userPW)
        var userPW_check = findViewById<EditText>(R.id.sign_up_userPW_check)




        var register_btn = findViewById<Button>(R.id.register_btn)
        register_btn.setOnClickListener {
            var edit_userID = userID.text.toString()
            var edit_userPW = userPW.text.toString()
            var edit_userPW_check = userPW_check.text.toString()

            signUp(edit_userID, edit_userPW, edit_userPW_check)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun signUp(email: String, password: String, password_check: String) {
        if (email.isNotEmpty() and password.isNotEmpty() and password_check.isNotEmpty()) {
            if (password == password_check) {
                val details_Intent = Intent(this, Activity_create_info::class.java)
                details_Intent.putExtra("email", email)
                details_Intent.putExtra("password", password)

                startActivity(details_Intent)
            } else {
                Toast.makeText(
                    baseContext, "비밀번호를 다시 확인해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else{
            Toast.makeText(
                baseContext, "이메일 또는 비밀번호를 다시 확인해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun reload() {

    }


    companion object {
        private const val TAG = "EmailPassword"
    }

}