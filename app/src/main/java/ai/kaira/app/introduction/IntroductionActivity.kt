package ai.kaira.app.introduction

import ai.kaira.app.R
import ai.kaira.app.ViewModelFactory
import ai.kaira.app.databinding.ActivityIntroductionBinding
import ai.kaira.app.utils.LanguageConfig.Companion.getLanguageLocale
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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


        introductionViewModel = ViewModelProvider(this,viewModelFactory).get(IntroductionViewModel::class.java)

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

        introductionBinding?.submitButton?.setOnClickListener {
            val firstName: String = introductionBinding.firstNameEt.text.toString()
            val languageLocale: String = getLanguageLocale(applicationContext)
            introductionViewModel.createUser(firstName,languageLocale).observe(this,{
                Log.v("User",it.toString())
            })
        }

        introductionViewModel.onErrorLiveData?.observe(this, {
            Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT).show()
        })

        introductionViewModel.onLoadLiveData?.observe(this,{
            Toast.makeText(applicationContext,"Loading "+ it,Toast.LENGTH_SHORT).show()
        })
    }

}