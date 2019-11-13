package com.example.kmessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import android.content.Intent
import android.util.Log


class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            performLogin()
        }

        back_to_register_textview.setOnClickListener{
            finish()
        }
    }

    private fun performLogin() {
        val email = email_textedit_login.text.toString()
        val password = password_textview_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    Log.d("Login", "Successfully logged in: ${it.result.user.uid}")
                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    return@addOnCompleteListener
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to log in: ${it.message} ", Toast.LENGTH_SHORT).show()
            }
    }

}
