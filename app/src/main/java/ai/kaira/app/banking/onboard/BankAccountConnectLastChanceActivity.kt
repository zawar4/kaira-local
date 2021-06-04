package ai.kaira.app.banking.onboard

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.banking.institution.BankInstitutionsHostActivity
import ai.kaira.app.databinding.ActivityBankAccountConnectLastChanceBinding
import ai.kaira.app.home.MainActivity
import ai.kaira.app.survey.SurveyActivity
import ai.kaira.app.utils.Extensions.Companion.ignoreInstitutionAddition
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class BankAccountConnectLastChanceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBankAccountConnectLastChanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bank_account_connect_last_chance)
        binding.surveyButton.setOnClickListener {
            startActivity(Intent(this, SurveyActivity::class.java))
        }


        binding.connectBankAccountButton.setOnClickListener {
            startActivity(Intent(this, BankInstitutionsHostActivity::class.java))
        }

        binding.moreInfoButton.setOnClickListener {
            startActivity(Intent(this, SecurityInfoActivity::class.java))
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.laterButton.setOnClickListener {
            finish()
            ignoreInstitutionAddition()
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}