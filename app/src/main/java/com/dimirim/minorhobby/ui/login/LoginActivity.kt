package com.dimirim.minorhobby.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val CODE_SIGN_IN = 0

class LoginActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleLoginBtn.setOnClickListener {
            try {
                signIn()
            } catch (e: Exception) {
                e.printStackTrace()
                onSignInFailed()
            }
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        val intent = GoogleSignIn.getClient(this, gso).signInIntent
        startActivityForResult(intent, CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CODE_SIGN_IN) return

        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            firebaseAuthWithGoogle(task.result!!)
        } catch (e: Exception) {
            e.printStackTrace()
            onSignInFailed()
        }
    }

    private fun firebaseAuthWithGoogle(googleAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        lifecycleScope.launch {
            val user = auth.signInWithCredential(credential).await().user!!
            signedIn(user)
        }
    }

    private fun signedIn(user: FirebaseUser) {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            putExtra("id", user.uid)
            putExtra("email", user.email)
            putExtra("profile", user.photoUrl)
        }
        startActivity(intent)
    }

    private fun onSignInFailed() {
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
    }
}

