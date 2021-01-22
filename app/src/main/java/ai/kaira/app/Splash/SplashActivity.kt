package ai.kaira.app.Splash

import ai.kaira.app.Onboarding.OnboardActivity
import ai.kaira.app.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(SPLASH_TIME_OUT){
            //do something
            startActivity(Intent(applicationContext,OnboardActivity::class.java))
            // close this activity
            finish()
        }

    }
}