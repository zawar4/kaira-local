package ai.kaira.app.splash

import ai.kaira.app.onboarding.OnboardActivity
import ai.kaira.app.R
import ai.kaira.app.utils.Extensions.Companion.clearCache
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        clearCache();
        Timer().schedule(SPLASH_TIME_OUT){
            //do something
            startActivity(Intent(applicationContext,OnboardActivity::class.java))
            // close this activity
            finish()
        }

    }
}