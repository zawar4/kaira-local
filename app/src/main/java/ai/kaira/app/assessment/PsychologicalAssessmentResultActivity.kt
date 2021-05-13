package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.PsychologicalAssessmentResultViewModel
import ai.kaira.app.databinding.ActivityPsychologicalAssessmentResultBinding
import ai.kaira.app.introduction.IntroductionActivity
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
                     binding.psychologicalImageView.setImageResource(R.drawable.social_status)
                     binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_social_status)
                     binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_social_status)
                 }
                PsychologicalProfileType.AUTONOMY ->{
                    binding.psychologicalImageView.setImageResource(R.drawable.autonomy)
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_autonomy)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_autonomy)
                }
                PsychologicalProfileType.BENEVOLENCE ->{
                    binding.psychologicalImageView.setImageResource(R.drawable.benevolence)
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_benevolence)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_benevolence)
                }
                PsychologicalProfileType.HEDONISM->{
                    binding.psychologicalImageView.setImageResource(R.drawable.hedonism)
                    binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_hedonism)
                    binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_hedonism)
                }
            }
        }

        binding.closeBtn.setOnClickListener {
            var intent = Intent(this, IntroductionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
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
            var intent = Intent(this, IntroductionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}