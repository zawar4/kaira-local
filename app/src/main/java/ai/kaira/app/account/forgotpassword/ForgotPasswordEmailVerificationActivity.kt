package ai.kaira.app.account.forgotpassword

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityForgotPasswordEmailVerificationBinding
import ai.kaira.app.utils.UIUtils
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordEmailVerificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordEmailVerificationBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var loginViewModel: LoginViewModel
    var email = ""
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState?.getString("email") != null){
            email = savedInstanceState.getString("email").toString()
        }
        if(savedInstanceState?.getString("token") != null){
            token =  savedInstanceState.getString("token").toString()
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_email_verification)

        if(intent.hasExtra("email")){
            email =  intent.getStringExtra("email").toString()
        }
        if(intent.hasExtra("token")){
            token = intent.getStringExtra("token").toString()
        }

        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.onForgotPasswordVerificationEmailSent().observe(this){ sent ->
            sent.let{
                UIUtils.networkCallAlert(this,getString(R.string.authentication_creation_email_resent))
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.openEmailBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                this.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    R.string.no_email_application_installed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.sendAnotherEmailBtn.setOnClickListener {
            loginViewModel.sendForgotPasswordVerificationEmail(email,token)
        }

        loginViewModel.onLoad().observe(this) { loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        loginViewModel.onConnectivityError().observe(this) {
            UIUtils.networkConnectivityAlert(this)
        }

        loginViewModel.onError().observe(this) { error ->
            UIUtils.networkCallAlert(this, error)
        }

        if(token.isNotEmpty()){
            binding.sendAnotherEmailBtn.performClick()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("token", token)
            putString("email", email)
        }
        super.onSaveInstanceState(outState)
    }
}