package ai.kaira.data.introduction.datasource.network

import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface IntroductionNetworkDataSource {
    fun createUser(firstName: String, languageLocale: String) : MutableLiveData<KairaResult<User>>
}