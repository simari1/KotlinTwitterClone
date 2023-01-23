package com.simari1.kotlintwitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.simari1.kotlintwitterclone.databinding.ActivityLoginBinding

// https://proandroiddev.com/kotlin-synthetic-is-dead-long-live-viewbinding-kotlin-android-extensions-deprecated-10a66204d5fc
class LoginActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthTokenListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
    }
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    fun onLogin(v: View) {
        var proceed = true

        with(mBinding) {
            if (emailET.text.isNullOrEmpty()) {
                emailTIL.error = "Email is required"
                emailTIL.isErrorEnabled = true
                proceed = false
            }

            if (passwordET.text.isNullOrEmpty()) {
                passwordTIL.error = "Password is required"
                passwordTIL.isErrorEnabled = true
                proceed = false
            }

            if (proceed) {
                loginProgressLayout.visibility = View.VISIBLE
                firebaseAuth.signInWithEmailAndPassword(emailET.text.toString(), passwordET.text.toString())
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            loginProgressLayout.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "Login error: ${task.exception?.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        loginProgressLayout.visibility = View.GONE
                    }
            }
        }
    }

    fun goToSignUp(v: View) {

    }
}