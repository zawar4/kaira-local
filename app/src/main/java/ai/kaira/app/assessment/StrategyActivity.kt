package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.account.AccountCreationOnboardingActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.AssessmentViewModel
import ai.kaira.app.databinding.ActivityStrategyBinding
import ai.kaira.app.utils.UIUtils
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StrategyActivity : AppCompatActivity() {
    lateinit var binding: ActivityStrategyBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var assessmentViewModel: AssessmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_strategy)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)

        assessmentViewModel.onStrategyFetch().observe(this){
            it?.let{ strategy ->
                binding.strategySentenceReasonTextview.text = strategy.strategy.sentenceReason
                binding.stressSentenceTextview.text = strategy.stress.sentence
                binding.strategySentenceTwoTextview.text = strategy.strategy.sentence2
            }?:run{
                assessmentViewModel.finishActivity()
            }
        }
        assessmentViewModel.fetchStrategy()

        assessmentViewModel.onError().observe(this){
            UIUtils.networkCallAlert(this,it)
        }

        assessmentViewModel.onConnectivityError().observe(this, {
            UIUtils.networkConnectivityAlert(this)
        })

        binding.yesButton.setOnClickListener {
            startActivity(Intent(this,AccountCreationOnboardingActivity::class.java))
        }

        binding.noButton.setOnClickListener {
            startActivity(Intent(this, LastChanceActivity::class.java))
        }


        binding.backBtn.setOnClickListener {
            assessmentViewModel.finishActivity()
        }

        assessmentViewModel.onActivityFinish().observe(this){
            finish()
        }
    }
}