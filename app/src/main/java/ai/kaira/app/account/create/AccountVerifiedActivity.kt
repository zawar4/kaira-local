package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.forgotpassword.ForgotPasswordActivity
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAccountVerifiedBinding
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountVerifiedActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountVerifiedBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var accountCreateViewModel: AccountCreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_verified)
        accountCreateViewModel = ViewModelProvider(this, viewModelFactory).get(AccountCreateViewModel::class.java)

        intent?.data?.let{ uri ->
            binding.imageView2.visibility = INVISIBLE
            binding.textView20.visibility = INVISIBLE
            binding.textView21.visibility = INVISIBLE
            binding.loginBtn.visibility = INVISIBLE
            val url = Uri.parse(uri.toString()) as Uri
            val token = url.getQueryParameter("token").toString()
            accountCreateViewModel.verifyAccount(token)
            binding.loginBtn.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            accountCreateViewModel.onLoad().observe(this) { loading ->
                if(loading){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }

            accountCreateViewModel.onAccountVerified().observe(this){
                binding.imageView2.visibility = VISIBLE
                binding.textView20.visibility = VISIBLE
                binding.textView21.visibility = VISIBLE
                binding.loginBtn.visibility = VISIBLE
            }

            accountCreateViewModel.onErrorAction().observe(this){ errorAction ->
                when(errorAction.kairaAction){
                    KairaAction.TOKEN_EXPIRED_RESEND ->{
                        val runnable : () -> Unit = {
                            val intent = Intent(this, AccountVerificationActivity::class.java)
                            intent.putExtra("token",token)
                            startActivity(intent)
                            finish()
                        }
                        UIUtils.networkCallAlert(this, errorAction.message,runnable)
                    }
                }
            }

            accountCreateViewModel.onConnectivityError().observe(this) {
                UIUtils.networkConnectivityAlert(this)
            }

            accountCreateViewModel.onError().observe(this) { error ->
                UIUtils.networkCallAlert(this, error)
            }
        }?:run{
            finish()
        }



    }
}