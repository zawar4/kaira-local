package ai.kaira.app.introduction

import ai.kaira.app.R
import ai.kaira.app.ViewModelFactory
import ai.kaira.app.databinding.ActivityIntroductionBinding
import ai.kaira.app.utils.LanguageConfig.Companion.getLanguageLocale
import ai.kaira.app.utils.UIUtils.Companion.networkCallAlert
import ai.kaira.app.utils.UIUtils.Companion.networkContectivityAlert
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    lateinit var introductionBinding: ActivityIntroductionBinding
    lateinit var introductionViewModel: IntroductionViewModel

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introductionBinding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        introductionViewModel = ViewModelProvider(this, viewModelFactory).get(IntroductionViewModel::class.java)
        introductionBinding?.firstNameEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (introductionBinding?.firstNameEt?.text.isNotEmpty() && introductionBinding?.submitButton?.visibility == INVISIBLE) {
                    introductionBinding?.submitButton?.visibility = VISIBLE
                } else if (introductionBinding?.firstNameEt?.text.isEmpty() && introductionBinding?.submitButton?.visibility == VISIBLE) {
                    introductionBinding?.submitButton?.visibility = INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        introductionBinding.firstNameEt.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit()
                }
                return false;
            }
        })

        introductionBinding.submitButton.setOnClickListener {
            submit()
        }

        introductionViewModel.onErrorLiveData.observe(this, {
            networkCallAlert(this,it)
        })

        introductionViewModel.onLoadLiveData.observe(this, {
            Toast.makeText(applicationContext, "Loading " + it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun submit(){
        val firstName: String = introductionBinding.firstNameEt.text.toString()
        val languageLocale: String = getLanguageLocale(applicationContext)
        if(introductionViewModel.isConnectedToInternet()){
            introductionViewModel.createUser(firstName, languageLocale).observe(this, {
                //TODO start next screen
            })
        }else{
            networkContectivityAlert(this)
        }
    }

}