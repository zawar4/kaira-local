package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityTermsConditionBinding
import ai.kaira.app.databinding.ActivityTermsOfUseBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class TermsConditionActivity : AppCompatActivity() {

    lateinit var termsConditionBinding: ActivityTermsConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        termsConditionBinding = DataBindingUtil.setContentView(this,R.layout.activity_terms_condition)
        termsConditionBinding?.privacyPolicyBtn?.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        termsConditionBinding?.termsOfUseBtn?.setOnClickListener {
            startActivity(Intent(this, TermsOfUseActivity::class.java))
        }

    }
}