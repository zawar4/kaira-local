package ai.kaira.app.banking

import ai.kaira.app.R
import ai.kaira.app.assessment.LastChanceActivity
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
            startActivity(Intent(this,SecurityInfoActivity::class.java))
        }

        binding.continueButton.setOnClickListener {
            //TODO connect bank account activity
        }

        binding.noButton.setOnClickListener {
            startActivity(Intent(this,BankAccountConnectLastChanceActivity::class.java))
        }
    }
}