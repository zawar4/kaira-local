package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAccountVerifiedBinding
import ai.kaira.app.utils.UIUtils
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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
            val url = Uri.parse(uri.toString()) as Uri
            val token = url.getQueryParameter("token").toString()
            accountCreateViewModel.verifyAccount(token)
        }?:run{
            finish()
        }

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
        }

        accountCreateViewModel.onConnectivityError().observe(this) {
            UIUtils.networkConnectivityAlert(this)
        }

        accountCreateViewModel.onError().observe(this) { error ->
            UIUtils.networkCallAlert(this, error)
        }

    }
}