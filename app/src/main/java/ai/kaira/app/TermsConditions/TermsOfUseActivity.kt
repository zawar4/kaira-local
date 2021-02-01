package ai.kaira.app.TermsConditions

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityTermsOfUseBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.ConfigurationCompat
import androidx.databinding.DataBindingUtil

class TermsOfUseActivity : AppCompatActivity() {

    lateinit var termsOfUseBinding: ActivityTermsOfUseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        termsOfUseBinding = DataBindingUtil.setContentView(this,R.layout.activity_terms_of_use)
        val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
        termsOfUseBinding.termsOfUseWebview.loadUrl("file:///android_asset/terms_of_use_en_ca.html")
    }
}