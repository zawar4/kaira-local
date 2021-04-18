package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.AssessmentViewModel
import ai.kaira.app.databinding.ActivityUserIdentityCreateAccountBinding
import ai.kaira.app.utils.Extensions.Companion.dismissKeyboard
import ai.kaira.app.utils.UIUtils
import android.accounts.Account
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserIdentityCreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserIdentityCreateAccountBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var accountCreateViewModel:AccountCreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_identity_create_account)
        binding.parent.setOnClickListener {
            dismissKeyboard()
        }
        accountCreateViewModel = ViewModelProvider(this, viewModelFactory).get(AccountCreateViewModel::class.java)
        binding.nextBtn.isClickable = false
        binding.backBtn.setOnClickListener {
            finish()
        }
        accountCreateViewModel.fetchUser().observe(this){ user ->
             user?.let{
                 binding.firstNameEt.setText(user.firstName)
             }
        }

        accountCreateViewModel.onLoad().observe(this){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.firstNameEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(binding.lastNameEt.text.trim().isNotEmpty() && binding.firstNameEt.text.trim().isNotEmpty()){
                        binding.nextBtn.alpha = 1.0f
                        binding.nextBtn.isClickable = true
                    } else {

                        binding.nextBtn.alpha = 0.5f
                        binding.nextBtn.isClickable = false
                    }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.lastNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.lastNameEt.text.isNotBlank() && binding.firstNameEt.text.isNotBlank()){
                    binding.nextBtn.alpha = 1.0f
                    binding.nextBtn.isClickable = true
                } else {

                    binding.nextBtn.alpha = 0.5f
                    binding.nextBtn.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        accountCreateViewModel.onConnectivityError().observe(this){
            UIUtils.networkConnectivityAlert(this)
            }

        accountCreateViewModel.onError().observe(this) { error ->
            UIUtils.networkCallAlert(this,error)
        }

        accountCreateViewModel.onGroupCodeExists().observe(this) { exists ->
            if(exists){
                    startActivity(Intent(this,UserCredentialsCreateAccountActivity::class.java))
                    binding.groupCodeEt.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
            }else{
                UIUtils.networkCallAlert(this,getString(R.string.authentication_creation_group_invalide),getString(R.string.action_yes),getString(R.string.action_no)) {
                    val intent = Intent(this,UserCredentialsCreateAccountActivity::class.java)
                    intent.putExtra("firstName",binding.firstNameEt.text.toString())
                    intent.putExtra("lastName",binding.lastNameEt.text.toString())
                    if(binding.groupCodeEt.text.toString().length == 8) {
                        intent.putExtra("groupCode",binding.groupCodeEt.text.toString())
                    }
                    startActivity(intent)
                }
                binding.groupCodeEt.setTextColor(Color.RED)
            }
        }

        binding.nextBtn.setOnClickListener {
            val groupCode = binding.groupCodeEt.text.toString()
            if(groupCode.isNotBlank()){
                if(accountCreateViewModel.validateGroupCode(groupCode)){
                    binding.groupCodeRuleTv.setTextColor(ContextCompat.getColor(applicationContext,R.color.kairaSecLabel))
                    accountCreateViewModel.groupCodeExists(groupCode)
                }else{
                    binding.groupCodeRuleTv.setTextColor(Color.RED)
                }
            }else{
                val intent = Intent(this,UserCredentialsCreateAccountActivity::class.java)
                intent.putExtra("firstName",binding.firstNameEt.text.toString())
                intent.putExtra("lastName",binding.lastNameEt.text.toString())
                startActivity(intent)
            }
        }
    }
}