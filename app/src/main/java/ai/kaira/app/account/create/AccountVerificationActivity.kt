package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAccountVerificationBinding
import ai.kaira.app.utils.UIUtils
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AccountVerificationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAccountVerificationBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private lateinit var accountCreateViewModel: AccountCreateViewModel
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

        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_verification)

        if(intent.hasExtra("email")){
            email =  intent.getStringExtra("email").toString()
        }
        if(intent.hasExtra("token")){
            token = intent.getStringExtra("token").toString()
        }

        accountCreateViewModel = ViewModelProvider(this, viewModelFactory).get(AccountCreateViewModel::class.java)

        accountCreateViewModel.onVerificationEmailSent().observe(this){ sent ->
            sent?.let{
                UIUtils.networkCallAlert(this,getString(R.string.authentication_creation_email_resent))
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.openEmailBtn.setOnClickListener {
            try {
                val intent : Intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,Intent.CATEGORY_APP_EMAIL)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            accountCreateViewModel.sendVerificationEmail(email,token)
        }

        accountCreateViewModel.onLoad().observe(this) { loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        accountCreateViewModel.onConnectivityError().observe(this) {
            UIUtils.networkConnectivityAlert(this)
        }

        accountCreateViewModel.onError().observe(this) { error ->
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