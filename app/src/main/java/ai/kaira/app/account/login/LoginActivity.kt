package ai.kaira.app.account.login

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityLoginBinding
import ai.kaira.app.utils.UIUtils
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
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.emailTv.addTextChangedListener(textWatcher)

        binding.passwordEt.addTextChangedListener(textWatcher)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailTv.text.toString()
            val password = binding.passwordEt.text.toString()
            loginViewModel.login(email,password)
        }
        loginViewModel.onUserLoggedIn().observe(this){

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

        loginViewModel.onConnectivityError().observe(this){
            UIUtils.networkConnectivityAlert(this)
        }

        loginViewModel.onError().observe(this){ error ->
            UIUtils.networkCallAlert(this, error)
        }
    }
}