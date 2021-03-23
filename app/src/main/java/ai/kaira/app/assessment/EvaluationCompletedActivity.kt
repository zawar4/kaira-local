package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.AssessmentViewModel
import ai.kaira.app.databinding.ActivityEvaluationCompletedBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EvaluationCompletedActivity : AppCompatActivity() {

    lateinit var binding : ActivityEvaluationCompletedBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var assessmentViewModel: AssessmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_evaluation_completed)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)

        assessmentViewModel.fetchStrategy().observe(this){
            it?.let{ strategy ->
                binding.strategySentenceTextview.text = strategy.strategy.sentence
            }
        }

        binding.continueBtn.setOnClickListener {

        }


        binding.backBtn.setOnClickListener {
            assessmentViewModel.finishActivity()
        }

        assessmentViewModel.onActivityFinish().observe(this){
            finish()
        }
    }
}