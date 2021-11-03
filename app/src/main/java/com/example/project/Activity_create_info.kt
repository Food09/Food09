package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Validators.and
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_info.*

class Activity_create_info : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_info)

        auth = Firebase.auth


        var email: String? = intent.getStringExtra("email")
        var password: String? = intent.getStringExtra("password")




        var complete_btn = findViewById<Button>(R.id.complete_btn)
        complete_btn.setOnClickListener {
            var displayName = sign_up_displayName.text.toString()
            var displayName_check = sign_up_displayName_check.text.toString()
            var my_info = my_info.text.toString()

            if (email != null && password != null) {
                signUp(email, password, displayName, displayName_check, my_info)
            }
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

    private fun signUp(email: String, password: String, displayName: String, displayName_check:String, my_info:String) {
        if (email.isNotEmpty() and password.isNotEmpty()) {
            if (displayName == displayName_check) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user, displayName, my_info)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "이메일 또는 비밀번호를 다시 확인해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // updateUI(null)
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext, "별명을 다시 확인해주세요.",
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

    private fun updateUI(user: FirebaseUser?, display_name: String, my_info: String) {
        if (user != null){
            // [+] go to register

            val profileUpdates = userProfileChangeRequest {
                // Todo : display_name변수는 nickname
                // Todo : my_info 변수는 자기소개
                displayName = display_name
                // photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val register_Intent = Intent(this, Register_Success::class.java)
                        startActivity(register_Intent)
                    }
                    else{
                        Log.d(TAG, "Error")
                    }
                }


        }

    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}
