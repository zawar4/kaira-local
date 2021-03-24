package ai.kaira.app.survey

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivitySurveyBinding
import ai.kaira.app.utils.Consts
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_survey)
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.webViewClient = WebViewClient()
        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.loadUrl(Consts.SURVEY_URL)
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    private class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            if (url != null) {
                view.loadUrl(url)
            }
            return true
        }
    }
}