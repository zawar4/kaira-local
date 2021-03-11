package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityFinancialProfilesBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class FinancialProfilesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFinancialProfilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_financial_profiles)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}