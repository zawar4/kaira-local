package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityPsychologicalAssessmentResultBinding
import ai.kaira.domain.assessment.model.PsychologicalProfileType
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PsychologicalAssessmentResultActivity : AppCompatActivity() {


    lateinit var psychologicalAssessmentResultViewModel: PsychologicalAssessmentResultViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var binding: ActivityPsychologicalAssessmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_psychological_assessment_result)
        psychologicalAssessmentResultViewModel  = ViewModelProvider(this, viewModelFactory).get(PsychologicalAssessmentResultViewModel::class.java)


        psychologicalAssessmentResultViewModel.fetchPsychologicalAssessmentProfile()


        psychologicalAssessmentResultViewModel.onPsychologicalAssessmentProfileFetched().observe(this){
            when(it.type){
                 PsychologicalProfileType.SOCIAL_STATUS ->{
                     binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_social_status)
                     binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_social_status)
                 }
                PsychologicalProfileType.AUTONOMY ->{
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_autonomy)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_autonomy)
                }
                PsychologicalProfileType.BENEVOLENCE ->{
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_benevolence)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_benevolence)
                }
                PsychologicalProfileType.HEDONISM->{
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_hedonism)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_hedonism)
                }
            }
        }

        binding.closeBtn.setOnClickListener {
            psychologicalAssessmentResultViewModel.finishActivity()
        }

        binding.backBtn.setOnClickListener {
            psychologicalAssessmentResultViewModel.finishActivity()
        }


        psychologicalAssessmentResultViewModel.onActivityFinish().observe(this){
            finish()
        }

        binding.detailBtn.setOnClickListener {
            startActivity(Intent(this, PsychologicalProfilesActivity::class.java))
        }

        binding.submitBtn.setOnClickListener {
            psychologicalAssessmentResultViewModel.finishActivity()
        }
    }
}