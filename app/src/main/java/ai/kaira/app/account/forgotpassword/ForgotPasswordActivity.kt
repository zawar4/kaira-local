package ai.kaira.app.account.forgotpassword

import ai.kaira.app.R
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityForgotPasswordBinding
import ai.kaira.app.utils.UIUtils
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var loginViewModel: LoginViewModel

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailTv.text.toString()
            binding.sendBtn.isEnabled = loginViewModel.isValidEmail(email)

            if(loginViewModel.isValidEmail(email)){
                binding.emailInvalidErrorTv.visibility = View.INVISIBLE
            }else{
                binding.emailInvalidErrorTv.visibility = View.VISIBLE
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)

        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.emailTv.addTextChangedListener(textWatcher)

        loginViewModel.onConnectivityError().observe(this){
            UIUtils.networkConnectivityAlert(this)
        }


        binding.sendBtn.setOnClickListener {
            val email = binding.emailTv.text.toString()
            loginViewModel.forgotPassword(email)
        }

        loginViewModel.onForgotPassword().observe(this){
            val email = binding.emailTv.text.toString()
            val intent = Intent(this,ForgotPasswordEmailVerificationActivity::class.java)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        loginViewModel.onError().observe(this){ error ->
            UIUtils.networkCallAlert(this, error)
        }

        loginViewModel.onLoad().observe(this){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.passwordFoundBtn.setOnClickListener {
            finish()
        }


    }
}