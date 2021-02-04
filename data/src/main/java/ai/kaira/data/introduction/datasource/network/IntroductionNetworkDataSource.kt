package ai.kaira.data.introduction.datasource.network

import ai.kaira.domain.Result
import ai.kaira.data.introduction.dto.User
import androidx.lifecycle.MutableLiveData

interface IntroductionNetworkDataSource {

    fun createUser(firstName: String, languageLocale: String) : MutableLiveData<Result<User>>
}