package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.LanguageConfig.Companion.CANADIAN_ENGLISH
import ai.kaira.app.utils.LanguageConfig.Companion.CANADIAN_FRENCH
import ai.kaira.app.utils.LanguageConfig.Companion.FRENCH
import ai.kaira.app.databinding.ActivityTermsOfUseBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class TermsOfUseActivity : AppCompatActivity() {

    lateinit var termsOfUseBinding: ActivityTermsOfUseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        termsOfUseBinding = DataBindingUtil.setContentView(this,R.layout.activity_terms_of_use)

        val currentLanguageLocale = LanguageConfig.getLanguageLocale(applicationContext)
        if(currentLanguageLocale == CANADIAN_FRENCH || currentLanguageLocale == FRENCH){
            termsOfUseBinding.termsOfUseWebview.loadUrl("file:///android_asset/terms_of_use_fr_ca.html")
        }else if(currentLanguageLocale == CANADIAN_ENGLISH){
            termsOfUseBinding.termsOfUseWebview.loadUrl("file:///android_asset/terms_of_use_en_ca.html")
        }else{
            termsOfUseBinding.termsOfUseWebview.loadUrl("file:///android_asset/terms_of_use_en_ca.html")
        }
    }
}