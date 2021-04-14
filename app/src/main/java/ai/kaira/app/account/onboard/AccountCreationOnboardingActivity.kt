package ai.kaira.app.account.onboard

import ai.kaira.app.R
import ai.kaira.app.account.create.InfoCreateAccountActivity
import ai.kaira.app.databinding.ActivityAccountCreationOnboardingBinding
import android.content.Intent
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

        binding.createAccountButton.setOnClickListener {
            startActivity(Intent(this, InfoCreateAccountActivity::class.java))
        }
    }
}