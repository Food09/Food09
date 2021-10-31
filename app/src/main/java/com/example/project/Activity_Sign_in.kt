package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*

class Activity_Sign_in : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // 인증 객체 초기화
        auth = Firebase.auth

        var edit_userID = findViewById<EditText>(R.id.userID_inputBox)
        var edit_userPW = findViewById<EditText>(R.id.userPW_inputBox)


        // 로그인 버튼
        val login_btn = findViewById<TextView>(R.id.login_btn)
        login_btn.setOnClickListener {
            var userID = edit_userID.text.toString()
            var userPW = edit_userPW.text.toString()
            signIn(userID, userPW)
        }

        // 회원가입 버튼
        val sign_up_btn = findViewById<TextView>(R.id.sign_up_btn)
        sign_up_btn.setOnClickListener {
            val sign_up_Intent = Intent(this, Activity_Sign_up::class.java)
            startActivity(sign_up_Intent)
        }

    }
    public override fun onStart() {
        super.onStart()
        // [+] 이미 로그인 되어있는지 확인
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isEmpty() or password.isEmpty()){
            Toast.makeText(baseContext, "이메일 또는 비밀번호를 입력해주세요.",
                Toast.LENGTH_SHORT).show()
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "이메일 또는 비밀번호를 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // updateUI(null)
                    }
                }
        }
    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            // [+] go to main Activity
            val main_Intent = Intent(this, MainActivity::class.java)
            startActivity(main_Intent)
        }
    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }




}


