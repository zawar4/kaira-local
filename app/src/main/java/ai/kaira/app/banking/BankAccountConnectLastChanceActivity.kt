package ai.kaira.app.banking

import ai.kaira.app.R
import ai.kaira.app.account.create.InfoCreateAccountActivity
import ai.kaira.app.databinding.ActivityBankAccountConnectLastChanceBinding
import ai.kaira.app.survey.SurveyActivity
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
            //TODO connect bank account activity
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}