package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.RedirectHelper.Companion.enableRedirect
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.onboard.BankAccountInvitationActivity
import ai.kaira.app.databinding.ActivityUserCredentialsCreateAccountBinding
import ai.kaira.app.utils.Extensions.Companion.dismissKeyboard
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.app.utils.UIUtils.Companion.networkConnectivityAlert
import ai.kaira.domain.banking.institution.model.BankingAggregator
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
    var firstName = ""
    var lastName = ""
    var groupCode = ""
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

        if(savedInstanceState?.getString("firstName") != null){
            firstName = savedInstanceState.getString("firstName").toString()
        }
        if(savedInstanceState?.getString("lastName") != null){
            lastName =  savedInstanceState.getString("lastName").toString()
        }
        if(savedInstanceState?.getString("groupCode") != null){
            lastName =  savedInstanceState.getString("groupCode").toString()
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_credentials_create_account)
        binding.parent.setOnClickListener {
            dismissKeyboard()
        }
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
                accountCreateViewModel.createAccount(firstName,lastName,currentLanguageLocale,email,password,groupCode,bankingAggregator = BankingAggregator.wealthica.value)

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
            enableRedirect(LoginActivity::class.java.simpleName, BankAccountInvitationActivity::class.java.simpleName)
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

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("firstName", firstName)
            putString("lastName", lastName)
            putString("groupCode", groupCode)
        }
        super.onSaveInstanceState(outState)
    }
}