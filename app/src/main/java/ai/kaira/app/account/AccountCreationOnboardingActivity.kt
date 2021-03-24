package ai.kaira.app.account

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityAccountCreationOnboardingBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class AccountCreationOnboardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAccountCreationOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_creation_onboarding)
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}