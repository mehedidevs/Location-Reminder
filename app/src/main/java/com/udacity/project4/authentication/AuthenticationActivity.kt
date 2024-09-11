package com.udacity.project4.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.locationreminders.RemindersActivity
import com.udacity.project4.utils.showToast

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
private const val REQUEST_CODE = 1

class AuthenticationActivity : AppCompatActivity() {
    
    private lateinit var loginButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        
        FirebaseAuth.getInstance().currentUser.let {
            if (it != null){
                startActivity(Intent(this, RemindersActivity::class.java))
                finish()
            }
            
            
        }
        
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            firebaseSignIn()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                showToast("user successfully signed in: ${FirebaseAuth.getInstance().currentUser?.displayName}!")
                val intent = Intent(this, RemindersActivity::class.java)
                startActivity(intent)
            } else {
                showToast("unsuccessful sign in: ${response?.error?.errorCode}")
            }
        }
    }
    
    private fun firebaseSignIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), REQUEST_CODE
        )
    }
}
