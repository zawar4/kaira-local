package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityPsychologicalAssessmentResultBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PsychologicalAssessmentResultActivity : AppCompatActivity() {


    lateinit var assessmentViewModel: AssessmentViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var binding: ActivityPsychologicalAssessmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_psychological_assessment_result)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)


        binding.closeBtn.setOnClickListener {
            assessmentViewModel.finishActivity()
        }

        binding.backBtn.setOnClickListener {
            assessmentViewModel.finishActivity()
        }


        assessmentViewModel.onActivityFinish().observe(this){
            finish()
        }

        binding.submitBtn.setOnClickListener {
            assessmentViewModel.finishActivity()
        }
    }
}