package ai.kaira.app.account.login

import ai.kaira.app.R
import ai.kaira.app.account.create.AccountVerificationActivity
import ai.kaira.app.account.create.InfoCreateAccountActivity
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.forgotpassword.ForgotPasswordActivity
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.BankAccountInvitationActivity
import ai.kaira.app.databinding.ActivityLoginBinding
import ai.kaira.app.utils.Extensions.Companion.dismissKeyboard
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import ai.kaira.domain.account.login.usecase.ForgotPassword
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var loginViewModel: LoginViewModel

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailTv.text.toString()
            val password = binding.passwordEt.text.toString()
            binding.loginBtn.isEnabled = loginViewModel.isValidEmail(email) &&
                    loginViewModel.isValidPassword(password)

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.parent.setOnClickListener {
            dismissKeyboard()
        }
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.emailTv.addTextChangedListener(textWatcher)

        binding.passwordEt.addTextChangedListener(textWatcher)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailTv.text.toString()
            val password = binding.passwordEt.text.toString()
            val timezone = TimeZone.getDefault().id
            loginViewModel.login(email,password,timezone)
        }


        binding.forgotPasswordBtn.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }

        loginViewModel.onUserLoggedIn().observe(this){
            startActivity(Intent(this, BankAccountInvitationActivity::class.java))
        }

        loginViewModel.onLoad().observe(this){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }


        binding.passwordVisibilityBtn.setOnClickListener {
            if(binding.passwordEt.transformationMethod == null){
                binding.passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.passwordVisibilityBtn.setImageResource(R.drawable.visibility_off)
            }else{
                binding.passwordEt.transformationMethod = null
                binding.passwordVisibilityBtn.setImageResource(R.drawable.visibility_on)
            }
            binding.passwordEt.setSelection(binding.passwordEt.text.length)
        }

        loginViewModel.onErrorAction().observe(this){ action ->
            if(action.kairaAction == KairaAction.UNVERIFIED_REDIRECT){
                val email = binding.emailTv.text.toString()
                loginViewModel.sendVerificationEmail(email)
            }
        }

        loginViewModel.onVerificationEmailSent().observe(this){
            val email = binding.emailTv.text.toString()
            val intent = Intent(this, AccountVerificationActivity::class.java)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        loginViewModel.onConnectivityError().observe(this){
            UIUtils.networkConnectivityAlert(this)
        }

        loginViewModel.onError().observe(this){ error ->
            UIUtils.networkCallAlert(this, error)
        }
    }
}