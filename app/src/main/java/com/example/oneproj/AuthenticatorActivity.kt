package com.example.oneproj

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class AuthenticatorActivity : AppCompatActivity() {
    private var loginFormAuth0LoginButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticator)
        loginFormAuth0LoginButton  = findViewById(R.id.button)
        val auth0Settings = Auth0(this).apply {
            isOIDCConformant = true
        }

        val audience = String.format("https://%s/userinfo", resources.getString(R.string.com_auth0_domain))
        loginFormAuth0LoginButton?.setOnClickListener {
            WebAuthProvider.init(auth0Settings)
                    .withAudience(audience)
                    .start(this@AuthenticatorActivity, object : AuthCallback {
                        override fun onFailure(dialog: Dialog) {
                            Log.d("TAG", "Auth0 Error displayed in dialog")
                            runOnUiThread { dialog.show() }
                        }
                        override fun onFailure(exception: AuthenticationException) {
                            runOnUiThread { toast("Auth0 Authentication Failed") }
                            Log.e("TAG", "Auth0 Error: ${exception?.localizedMessage}")
                        }
                        override fun onSuccess(credentials: Credentials) {
                            runOnUiThread { toast("Auth0 Authenticated Successfully") }
                            Log.d("TAG", "ID Token = ${credentials.idToken}")
                        }


                    })
        }




    }
    fun toast(abc: String ){
        val toast = Toast.makeText(applicationContext, abc, Toast.LENGTH_LONG)
        toast.show()

    }

}

