package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityPrivacyPolicyBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.ConfigurationCompat
import androidx.databinding.DataBindingUtil

class PrivacyPolicyActivity : AppCompatActivity() {


    lateinit var privacyPolicyBinding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privacyPolicyBinding = DataBindingUtil.setContentView(this,R.layout.activity_privacy_policy)
        val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0].toLanguageTag()
        if(currentLocale.equals("fr-CA") || currentLocale.equals("fr-FR")){
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_fr_ca.html")
        }else if(currentLocale.equals("en-CA")){
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_en_ca.html")
        }else{
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_en_ca.html")
        }

    }
}