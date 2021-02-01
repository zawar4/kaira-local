package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityTermsConditionBinding
import ai.kaira.app.databinding.ActivityTermsOfUseBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class TermsConditionActivity : AppCompatActivity() {

    lateinit var termsConditionBinding: ActivityTermsConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        termsConditionBinding = DataBindingUtil.setContentView(this,R.layout.activity_terms_condition)
    }
}