package ai.kaira.app.banking.institution

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.ActivityInstitutionSecurityAnswerBinding
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import ai.kaira.domain.banking.institution.model.BankingAggregator
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InstitutionSecurityAnswerActivity : AppCompatActivity() {

    lateinit var institutionViewModel: InstitutionViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var binding : ActivityInstitutionSecurityAnswerBinding

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.submitBtn.isEnabled = binding.userPinEt.text.length > 1
        }
        override fun afterTextChanged(s: Editable?) {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_institution_security_answer)
        institutionViewModel = ViewModelProvider(this,viewModelFactory).get(InstitutionViewModel::class.java)

        intent?.let {
            if(intent.hasExtra("institution")){
                val institution = intent.getSerializableExtra("institution") as Institution
                Glide.with(binding.institutionIm.context).load(institution.getLogoUrl()).into(binding.institutionIm);
                binding.submitBtn.setOnClickListener {
                    val answer = binding.userPinEt.text.toString()
                    val institutionId = institution.id
                    val aggregator = BankingAggregator.wealthica.value
                    institutionId?.let { it1 ->
                        institutionViewModel.submitAccountVerificationCode(aggregator, SecurityAnswer(answer), it1)
                    }
                }
            }
        }

        institutionViewModel.onInstitutionAccountVerified().observe(this) {
            val intent = Intent()
            intent.putExtra("refresh_my_financial",true)
            setResult(RESULT_OK,intent)
            finish()
        }


        institutionViewModel.onLoad().observe(this){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        institutionViewModel.onConnectivityError().observe(this){
            UIUtils.networkConnectivityAlert(this)
        }

        institutionViewModel.onError().observe(this){ error ->
            UIUtils.networkCallAlert(this, error)
        }

        institutionViewModel.onErrorAction().observe(this) { error ->
            error.kairaAction?.let { action ->
                when (action) {
                    KairaAction.UNAUTHORIZED_REDIRECT -> {
                        UIUtils.networkCallAlert(this, error.message) {
                            finish()
                            var intent = Intent(this, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)

                        }
                    }
                }
            }
        }

        binding.userPinEt.addTextChangedListener(textWatcher)
    }
}