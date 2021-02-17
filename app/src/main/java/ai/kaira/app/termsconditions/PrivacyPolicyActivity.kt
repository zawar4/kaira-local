package ai.kaira.app.termsconditions

import ai.kaira.app.R
import ai.kaira.app.utils.LanguageConfig.Companion.CANADIAN_ENGLISH
import ai.kaira.app.utils.LanguageConfig.Companion.CANADIAN_FRENCH
import ai.kaira.app.utils.LanguageConfig.Companion.FRENCH
import ai.kaira.app.utils.LanguageConfig.Companion.getLanguageLocale
import ai.kaira.app.databinding.ActivityPrivacyPolicyBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class PrivacyPolicyActivity : AppCompatActivity() {


    lateinit var privacyPolicyBinding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privacyPolicyBinding = DataBindingUtil.setContentView(this,R.layout.activity_privacy_policy)

        val currentLanguageLocale = getLanguageLocale(applicationContext)
        if((currentLanguageLocale == CANADIAN_FRENCH || currentLanguageLocale == FRENCH)){
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_fr_ca.html")
        }else if(currentLanguageLocale == CANADIAN_ENGLISH){
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_en_ca.html")
        }else{
            privacyPolicyBinding.privacyPolicyWebview.loadUrl("file:///android_asset/privacy_policy_en_ca.html")
        }

    }
}