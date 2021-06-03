package ai.kaira.app.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KairaApplication : Application() {

    companion object{
        var creatingAccountFirstTime = false
    }

}