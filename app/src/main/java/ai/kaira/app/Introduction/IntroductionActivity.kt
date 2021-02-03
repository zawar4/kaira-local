package ai.kaira.app.Introduction

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityIntroductionBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class IntroductionActivity : AppCompatActivity() {
    lateinit var introductionBinding: ActivityIntroductionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introductionBinding = DataBindingUtil.setContentView(this,R.layout.activity_introduction)
    }
}