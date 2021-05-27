package ai.kaira.app.banking.onboard

import ai.kaira.app.R
import ai.kaira.app.banking.institution.BankInstitutionsHostActivity
import ai.kaira.app.databinding.ActivityBankAccountInvitationBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class BankAccountInvitationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBankAccountInvitationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bank_account_invitation)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.moreInfoButton.setOnClickListener {
            startActivity(Intent(this, SecurityInfoActivity::class.java))
        }

        binding.continueButton.setOnClickListener {
            startActivity(Intent(this,BankInstitutionsHostActivity::class.java))
        }

        binding.noButton.setOnClickListener {
            startActivity(Intent(this, BankAccountConnectLastChanceActivity::class.java))
        }
    }
}