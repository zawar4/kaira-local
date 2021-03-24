package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityLastChanceBinding
import ai.kaira.app.survey.SurveyActivity
import ai.kaira.app.utils.Consts.Companion.SURVEY_URL
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class LastChanceActivity : AppCompatActivity() {

    lateinit var binding : ActivityLastChanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_last_chance)
        binding.surveyButton.setOnClickListener {
           startActivity(Intent(this,SurveyActivity::class.java))
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}