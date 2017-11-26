package com.rodrigobresan.sampleboilerplateandroid.intro

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.rodrigobresan.sampleboilerplateandroid.R
import kotlinx.android.synthetic.main.activity_intro.*


class IntroActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 1
    private var googleSignInClient: GoogleSignInClient? = null

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("821594554448-ndfm7gm2sj8v9uhsk6trgklbban8ndpc.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions)

        firebaseAuth = FirebaseAuth.getInstance()
        setUpClickListeners()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth?.currentUser
        updateUi(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = signedInAccountFromIntent.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (exception: ApiException) {
                updateUi(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

        firebaseAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this, {
                    if (it.isSuccessful) {
                        val user = firebaseAuth?.currentUser
                        updateUi(user)
                    } else {
                        updateUi(null)
                    }
                })
    }

    private fun updateUi(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            Toast.makeText(this, "currentUser: " + currentUser.displayName, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpClickListeners() {
        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}