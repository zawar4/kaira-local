package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityUserCredentialsCreateAccountBinding
import ai.kaira.app.utils.Extensions.Companion.dismissKeyboard
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.app.utils.UIUtils.Companion.networkConnectivityAlert
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserCredentialsCreateAccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserCredentialsCreateAccountBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var accountCreateViewModel: AccountCreateViewModel

    private val textWatcher: TextWatcher = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailTv.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()
            binding.submitBtn.isEnabled = accountCreateViewModel.isValidEmail(email) &&
                    accountCreateViewModel.isValidPassword(password) &&
                    accountCreateViewModel.isValidPassword(confirmPassword) &&
                    accountCreateViewModel.arePasswordsSame(password,confirmPassword)

            if(accountCreateViewModel.isValidEmail(email)){
                //binding.emailTv.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
                binding.emailInvalidErrorTv.visibility = INVISIBLE
            }else{
                //binding.emailTv.setTextColor(Color.RED)
                binding.emailInvalidErrorTv.visibility = VISIBLE
            }

            if(accountCreateViewModel.isValidPassword(password)){
                binding.passwordRuleTv.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
                //binding.passwordEt.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))

                if(accountCreateViewModel.arePasswordsSame(password,confirmPassword)){
                    //binding.confirmPasswordEt.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
                    binding.confirmPasswordErrorTv.visibility = INVISIBLE
                }else{
                    binding.confirmPasswordErrorTv.visibility = VISIBLE
                    //binding.confirmPasswordEt.setTextColor(Color.RED)
                }
            }else{
                binding.passwordRuleTv.setTextColor(Color.RED)
                //binding.passwordEt.setTextColor(Color.RED)
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_credentials_create_account)
        binding.parent.setOnClickListener {
            dismissKeyboard()
        }
        var firstName = ""
        var lastName = ""
        var groupCode = ""
        if(intent !=null){
            if(intent.hasExtra("firstName")){
                firstName = intent.getStringExtra("firstName").toString()
            }
            if(intent.hasExtra("lastName")){
                lastName = intent.getStringExtra("lastName").toString()
            }
            if(intent.hasExtra("groupCode")){
                groupCode = intent.getStringExtra("groupCode").toString()
            }
            accountCreateViewModel = ViewModelProvider(this, viewModelFactory).get(AccountCreateViewModel::class.java)

            binding.emailTv.addTextChangedListener(textWatcher)

            binding.passwordEt.addTextChangedListener(textWatcher)

            binding.confirmPasswordEt.addTextChangedListener(textWatcher)


            val currentLanguageLocale = LanguageConfig.getLanguageLocale(applicationContext)
            binding.submitBtn.setOnClickListener {
                val email = binding.emailTv.text.toString()
                accountCreateViewModel.emailExists(email)
            }
            accountCreateViewModel.onEmailExists().observe(this){ exists ->
                if(exists){
                    UIUtils.networkCallAlert(this,getString(R.string.authentication_creation_identity_already_exist),getString(R.string.action_login),getString(R.string.action_cancel)) {
                        // TODO open login activity
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                } else{
                    val email = binding.emailTv.text.toString()
                    val password = binding.passwordEt.text.toString()
                    accountCreateViewModel.createAccount(firstName,lastName,currentLanguageLocale,email,password,groupCode)

                }
            }

            accountCreateViewModel.onConnectivityError().observe(this){
                networkConnectivityAlert(this)
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

            accountCreateViewModel.onAccountCreated().observe(this){
                val email = binding.emailTv.text.toString()
                val intent = Intent(this,AccountVerificationActivity::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
            }
            accountCreateViewModel.onLoad().observe(this){ loading ->
                if(loading){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }

            accountCreateViewModel.onError().observe(this){ error ->
                UIUtils.networkCallAlert(this, error)
            }

            binding.backBtn.setOnClickListener {
                finish()
            }
        }else{
            finish()
        }

    }
}