package ai.kaira.app.banking.institution.fragments

import android.os.Bundle
import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.BankInstitutionLoginHostActivity
import ai.kaira.app.banking.institution.BankInstitutionsHostActivity
import ai.kaira.app.banking.institution.InstitutionSecurityAnswerActivity
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.ActivityFinancialInstitutionBinding
import ai.kaira.app.databinding.FinanceItemLayoutBinding
import ai.kaira.app.databinding.FragmentMyFinanceBinding
import ai.kaira.app.home.MainActivity
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import ai.kaira.app.utils.Extensions.Companion.getFormattedAmount
import ai.kaira.app.utils.Extensions.Companion.getFormattedDate
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import ai.kaira.domain.banking.institution.model.BankingAggregator
import ai.kaira.domain.banking.institution.model.BankingInstitutionSyncErrorType
import ai.kaira.domain.banking.institution.model.BankingInstitutionSyncStatus
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinancialInstitutionActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK) {
            data?.let { intnt ->
                institutionViewModel.getMyInstitutions()
            }
        }
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel : InstitutionViewModel

    lateinit var binding : ActivityFinancialInstitutionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        institutionViewModel = ViewModelProvider(this,viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_financial_institution)

        binding.addFinancialInstitutions.setOnClickListener {
            val intent = Intent(this,BankInstitutionsHostActivity::class.java)
            intent.putExtra("from",FinancialInstitutionActivity::class.java.simpleName)
            startActivityForResult(intent,100)
        }
        institutionViewModel.onMyInstitutionsFetched().observe(this) { institutions ->
            binding.institutionsParent.removeAllViews()
            val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            institutions.forEachIndexed { index, institution ->
                val itembinding : ai.kaira.app.databinding.InstitutionViewLinearDashboardBinding = DataBindingUtil.inflate(inflater,R.layout.institution_view_linear_dashboard,binding.institutionsParent,false)
                itembinding.exploreBtn.visibility = VISIBLE
                Glide.with(this).load(institution.getLogoUrl()).into(itembinding.institutionIm)
                institution.name?.let {
                    itembinding.institutionNameTv.text = institution.name
                }
                institution.syncStatus?.let{ it ->
                    when(it) {
                        BankingInstitutionSyncStatus.ERROR -> {
                            institution.syncError?.let {
                                when(it.name) {
                                    BankingInstitutionSyncErrorType.SECURITY_QUESTION_ERROR -> {
                                        itembinding.root.setOnClickListener {
                                            val intent = Intent(this, InstitutionSecurityAnswerActivity::class.java)
                                            intent.putExtra("institution",institution)
                                            startActivityForResult(intent,100)
                                            // we display the security question page
                                        }
                                        itembinding.status.visibility = View.VISIBLE
                                        itembinding.status.background = getDrawable(R.drawable.status_verification_required)
                                        itembinding.status.text = getString(R.string.banking_institutions_status_security_question)
                                    }
                                    BankingInstitutionSyncErrorType.LOGIN_FAILED_ERROR -> {
                                        itembinding.root.setOnClickListener {
                                            val intent = Intent(this, BankInstitutionLoginHostActivity::class.java)
                                            intent.putExtra("institution_type",institution.type)
                                            intent.putExtra("from",FinancialInstitutionActivity::class.java.simpleName)
                                            startActivityForResult(intent,100)
                                            // we display the banking login page
                                        }
                                        itembinding.status.visibility = View.VISIBLE
                                        itembinding.status.background = getDrawable(R.drawable.status_error)
                                        itembinding.status.text = getString(R.string.error)
                                    }
                                    else -> {
                                        val intent = Intent(this , BankInstitutionLoginHostActivity::class.java)
                                        intent.putExtra("institution_type",institution.type)
                                        intent.putExtra("from",FinancialInstitutionActivity::class.java.simpleName)
                                        startActivityForResult(intent,100)
                                        // we display the bank login page
                                        itembinding.status.visibility = View.VISIBLE
                                        itembinding.status.background = getDrawable(R.drawable.status_error)
                                        itembinding.status.text = getString(R.string.error)
                                    }
                                }

                            }
                        }
                        BankingInstitutionSyncStatus.OK -> {
                            itembinding.status.visibility = View.GONE
                            itembinding.status.background = null
                        }
                        BankingInstitutionSyncStatus.SYNCING -> {
                            itembinding.root.setOnClickListener {
                                UIUtils.alert(
                                    this,
                                    getString(R.string.warning),
                                    getString(R.string.banking_institutions_status_syncing_alert)) {
                                    institutionViewModel.getMyInstitutions()
                                }
                            }
                            itembinding.status.visibility = View.VISIBLE
                            itembinding.status.background = getDrawable(R.drawable.status_syncing)
                            itembinding.status.text = getString(R.string.banking_institutions_status_syncing)
                        }
                        BankingInstitutionSyncStatus.RETRY -> {
                            val intent = Intent(this,
                                BankInstitutionLoginHostActivity::class.java)
                            intent.putExtra("institution_type",institution.type)
                            intent.putExtra("from",FinancialInstitutionActivity::class.java.simpleName)
                            startActivityForResult(intent,100)
                            // we display the bank login page
                            itembinding.status.visibility = View.VISIBLE
                            itembinding.status.background = getDrawable(R.drawable.status_error)
                            itembinding.status.text = getString(R.string.action_retry)
                        }
                    }
                }
                itembinding.root.setOnLongClickListener {
                    UIUtils.alertYesNo(this,getString(R.string.warning),getString(R.string.finance_institutions_remove_message)) {
                        institutionViewModel.removeInstitution(BankingAggregator.wealthica.value,institution.id.toString())
                    }
                    true
                }

                binding.institutionsParent.addView(itembinding.root)
            }
            binding.root.requestLayout()
        }

        institutionViewModel.onInstitutionRemoved().observe(this){
            institutionViewModel.getMyInstitutions()
        }

        institutionViewModel.getMyInstitutions()

        institutionViewModel.onLoad().observe(this){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        institutionViewModel.onConnectivityError().observe(this) {
            UIUtils.networkConnectivityAlert(this)
        }

        institutionViewModel.onErrorAction().observe(this){
            when(it.kairaAction){
                KairaAction.UNAUTHORIZED_REDIRECT ->{
                    UIUtils.networkCallAlert(this, it.message) {
                        finish()
                        var intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)

                    }
                }
            }
        }

    }

}