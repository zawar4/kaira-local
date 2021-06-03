package ai.kaira.app.splash

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.onboard.BankAccountInvitationActivity
import ai.kaira.app.home.MainActivity
import ai.kaira.app.onboarding.OnboardActivity
import ai.kaira.app.utils.Configuration
import ai.kaira.app.utils.Extensions.Companion.clearCache
import ai.kaira.app.utils.Extensions.Companion.ignoreInstitutionAddition
import ai.kaira.domain.KairaAction
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var splashViewModel: SplashViewModel
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCenter.start(application, Configuration.getAppCenterKey(), Analytics::class.java, Crashes::class.java)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        if(splashViewModel.isLoggedIn()){
            splashViewModel.onInstitutionFetched().observe(this){ exists ->
                if(exists){
                    ignoreInstitutionAddition()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(applicationContext, BankAccountInvitationActivity::class.java))
                    finish()
                }
            }
            splashViewModel.getMyInstitutions()
            splashViewModel.onErrorAction().observe(this) { error ->
                error.kairaAction?.let { action ->
                    when (action) {
                        KairaAction.UNAUTHORIZED_REDIRECT -> {
                            finish()
                            var intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }else{
            Timer().schedule(SPLASH_TIME_OUT){
                clearCache();
                startActivity(Intent(applicationContext,OnboardActivity::class.java))
                // close this activity
                finish()
            }
        }
    }
}