package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.FinancialAssessmentResultViewModel
import ai.kaira.app.databinding.ActivityFinancialAssessmentResultBinding
import ai.kaira.app.introduction.IntroductionActivity
import ai.kaira.domain.assessment.model.FinancialProfileType
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_assessment.view.*
import javax.inject.Inject

@AndroidEntryPoint
class FinancialAssessmentResultActivity : AppCompatActivity() {


    lateinit var financialAssessmentResultViewModel: FinancialAssessmentResultViewModel

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var binding : ActivityFinancialAssessmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_financial_assessment_result)
        financialAssessmentResultViewModel = ViewModelProvider(this,viewModelFactory).get(FinancialAssessmentResultViewModel::class.java)
        financialAssessmentResultViewModel.fetchFinancialAssessmentProfile()
        financialAssessmentResultViewModel.onFinancialAssessmentProfileFetched().observe(this){
            it?.let { profile ->
                binding.financialProfileScoreProgress.progress = profile.average.toInt()
                if(profile.average <= 30){
                    binding.financialProfileTitleTv.text = getString(R.string.financial_profile_title_bad)
                    binding.financialProfileScoreProgressSecondary.background = ContextCompat.getDrawable(this,R.drawable.secondary_ring_bad)
                    binding.financialProfileScoreProgress.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                }else if(profile.average > 30 && profile.average <=70){
                    binding.financialProfileTitleTv.text = getString(R.string.financial_profile_title_ok)
                    binding.financialProfileScoreProgressSecondary.background = ContextCompat.getDrawable(this,R.drawable.secondary_ring_ok)
                    binding.financialProfileScoreProgress.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                }else{
                    binding.financialProfileTitleTv.text = getString(R.string.financial_profile_title_good)
                    binding.financialProfileScoreProgressSecondary.background = ContextCompat.getDrawable(this,R.drawable.secondary_ring_good)
                    binding.financialProfileScoreProgress.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                }
                binding.financialProfileScoreTv.text = profile.average.toInt().toString()
                profile.types.forEach { profileValue ->
                    when(profileValue.type){
                        FinancialProfileType.SAVING ->{
                            binding.savingScoreTv.text = profileValue.average.toInt().toString()
                            binding.savingProgressBar.progress = profileValue.average.toInt()
                            if(profileValue.average <= 30){
                                binding.savingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                                binding.savingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                            }else if(profileValue.average > 30 && profileValue.average <=70){
                                binding.savingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                                binding.savingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                            }else{
                                binding.savingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                                binding.savingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                            }
                        }
                        FinancialProfileType.CREDIT ->{
                            binding.borrowingScoreTv.text = profileValue.average.toInt().toString()
                            binding.borrowingProgressBar.progress = profileValue.average.toInt()
                            if(profileValue.average <= 30){
                                binding.borrowingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                                binding.borrowingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                            }else if(profileValue.average > 30 && profileValue.average <=70){
                                binding.borrowingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                                binding.borrowingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                            }else{
                                binding.borrowingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                                binding.borrowingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                            }
                        }
                        FinancialProfileType.PLANNING ->{
                            binding.planningScoreTv.text = profileValue.average.toInt().toString()
                            binding.planningProgressBar.progress = profileValue.average.toInt()
                            if(profileValue.average <= 30){
                                binding.planningProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                                binding.planningProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                            }else if(profileValue.average > 30 && profileValue.average <=70){
                                binding.planningProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                                binding.planningProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                            }else{
                                binding.planningProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                                binding.planningProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                            }
                        }
                        FinancialProfileType.SPENDING ->{
                            binding.spendingScoreTv.text = profileValue.average.toInt().toString()
                            binding.spendingProgressBar.progress = profileValue.average.toInt()
                            if(profileValue.average <= 30){
                                binding.spendingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                                binding.spendingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileBad)))
                            }else if(profileValue.average > 30 && profileValue.average <=70){
                                binding.spendingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                                binding.spendingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileOk)))
                            }else{
                                binding.spendingProgressBar.progressBackgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                                binding.spendingProgressBar.progressTintList = (ColorStateList.valueOf(ContextCompat.getColor(this,R.color.financialProfileGood)))
                            }
                        }
                    }

                }
            }
        }

        binding.closeBtn.setOnClickListener {
            var intent = Intent(this, IntroductionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            financialAssessmentResultViewModel.finishActivity()
        }


        financialAssessmentResultViewModel.onActivityFinish().observe(this){
            finish()
        }

        binding.submitBtn.setOnClickListener {
            var intent = Intent(this, IntroductionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.detailBtn.setOnClickListener {
            startActivity(Intent(this, FinancialProfilesActivity::class.java))
        }
    }
}