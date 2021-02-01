package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityPrivacyPolicyBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class PrivacyPolicyActivity : AppCompatActivity() {


    lateinit var privacyPolicyBinding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privacyPolicyBinding = DataBindingUtil.setContentView(this,R.layout.activity_privacy_policy)
    }
}