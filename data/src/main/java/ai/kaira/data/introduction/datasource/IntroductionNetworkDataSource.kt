package ai.kaira.data.introduction.datasource

import ai.kaira.data.introduction.dto.User
import androidx.lifecycle.MutableLiveData
import org.jetbrains.annotations.NotNull

interface IntroductionNetworkDataSource {

    fun createUser(firstName: String, languageLocale: String) : MutableLiveData<User>
}