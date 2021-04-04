package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAccountVerificationBinding
import ai.kaira.app.utils.UIUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountVerificationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAccountVerificationBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var accountCreateViewModel: AccountCreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_verification)
        var email = ""
        intent?.let{
            if(intent.hasExtra("email")){
                email = intent.getStringExtra("email").toString()
                accountCreateViewModel = ViewModelProvider(this, viewModelFactory).get(AccountCreateViewModel::class.java)

                accountCreateViewModel.onVerificationEmailSent().observe(this){ sent ->
                        sent?.let{
                            UIUtils.networkCallAlert(this,getString(R.string.authentication_creation_email_resent))
                        }
                }
                binding.sendAnotherEmailBtn.setOnClickListener {
                    accountCreateViewModel.sendVerificationEmail(email)
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
            }
        }?: run {
            finish()
        }
    }
}