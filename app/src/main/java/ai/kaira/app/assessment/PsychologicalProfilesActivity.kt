package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityPsychologicalProfilesBinding
import ai.kaira.domain.assessment.model.PsychologicalProfileType
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil

class PsychologicalProfilesActivity : AppCompatActivity() {

    lateinit var binding : ActivityPsychologicalProfilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_psychological_profiles)


        binding.socialStatusView?.setOnClickListener {
            binding.socialStatusView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_border)
            binding.benevolenceView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.hedonismView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.autonomyView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)

            binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_social_status)
            binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_social_status)
        }

        binding.autonomyView?.setOnClickListener {
            binding.socialStatusView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.benevolenceView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.hedonismView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.autonomyView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_border)

        binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_autonomy)
        binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_autonomy)
        }

        binding.hedonismView?.setOnClickListener {
            binding.socialStatusView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.benevolenceView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.hedonismView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_border)
            binding.autonomyView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)

        binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_hedonism)
        binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_hedonism)
        }

        binding.benevolenceView?.setOnClickListener {
            binding.socialStatusView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.benevolenceView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_border)
            binding.hedonismView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)
            binding.autonomyView.background = ContextCompat.getDrawable(this,R.drawable.sec_fill_round)

        binding.psychologicalTypeTitleTv.text = getString(R.string.psychological_profile_type_benevolence)
        binding.psychologicalTypeDescriptionTv.text = getString(R.string.psychological_profile_description_benevolence)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}