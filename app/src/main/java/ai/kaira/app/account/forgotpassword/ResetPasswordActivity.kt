package ai.kaira.app.account.forgotpassword

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityForgotPasswordEmailVerificationBinding
import ai.kaira.app.databinding.ActivityResetPasswordBinding
import ai.kaira.app.utils.Extensions.Companion.dismissKeyboard
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResetPasswordBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var loginViewModel: LoginViewModel

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()
            binding.resetBtn.isEnabled =
                loginViewModel.isValidPassword(password) &&
                        loginViewModel.isValidPassword(confirmPassword) &&
                        loginViewModel.arePasswordsSame(password,confirmPassword)


            if(loginViewModel.arePasswordsSame(password,confirmPassword)){
                //binding.confirmPasswordEt.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
                binding.confirmPasswordErrorTv.visibility = View.INVISIBLE
            }else{
                binding.confirmPasswordErrorTv.visibility = View.VISIBLE
                //binding.confirmPasswordEt.setTextColor(Color.RED)
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reset_password)
        binding.parent.setOnClickListener {
            dismissKeyboard()
        }
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.passwordEt.addTextChangedListener(textWatcher)

        binding.confirmPasswordEt.addTextChangedListener(textWatcher)

        intent?.data?.let{ uri ->
            val url = Uri.parse(uri.toString()) as Uri
            val token = url.getQueryParameter("token").toString()
            binding.resetBtn.setOnClickListener {
                val password = binding.passwordEt.text.toString()
                loginViewModel.resetPassword(password,token)
            }

            loginViewModel.onPasswordReset().observe(this){
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }

            loginViewModel.onError().observe(this){ error ->
                UIUtils.networkCallAlert(this, error)
            }

            loginViewModel.onErrorAction().observe(this){ action ->
                if(action.kairaAction == KairaAction.TOKEN_EXPIRED_REDIRECT){
                    val runnable : () -> Unit = {
                        val intent = Intent(this,ForgotPasswordActivity::class.java)
                        //intent.putExtra("token",token)
                        startActivity(intent)
                        finish()
                    }
                    UIUtils.networkCallAlert(this, action.message,runnable)
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
            binding.confirmPasswordVisibilityBtn.setOnClickListener {
                if(binding.confirmPasswordEt.transformationMethod == null){
                    binding.confirmPasswordEt.transformationMethod = PasswordTransformationMethod()
                    binding.confirmPasswordVisibilityBtn.setImageResource(R.drawable.visibility_off)
                }else{
                    binding.confirmPasswordEt.transformationMethod = null
                    binding.confirmPasswordVisibilityBtn.setImageResource(R.drawable.visibility_on)
                }
                binding.confirmPasswordEt.setSelection(binding.confirmPasswordEt.text.length)
            }


            loginViewModel.onLoad().observe(this){ loading ->
                if(loading){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }?:run{
            finish()
        }


    }
}