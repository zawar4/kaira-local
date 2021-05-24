package ai.kaira.app.splash

import ai.kaira.app.R
import ai.kaira.app.banking.BankAccountInvitationActivity
import ai.kaira.app.onboarding.OnboardActivity
import ai.kaira.app.utils.Configuration
import ai.kaira.app.utils.Extensions.Companion.clearCache
import ai.kaira.app.utils.Extensions.Companion.isLoggedIn
import ai.kaira.data.utils.APIConfig
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(application, Configuration.getAppCenterKey(), Analytics::class.java, Crashes::class.java)

        setContentView(R.layout.activity_splash)
        Timer().schedule(SPLASH_TIME_OUT){
            //do something
            if(isLoggedIn()){
                startActivity(Intent(applicationContext, BankAccountInvitationActivity::class.java))
                finish()
            }else{
                clearCache();
                startActivity(Intent(applicationContext,OnboardActivity::class.java))
                // close this activity
                finish()
            }
        }

    }
}