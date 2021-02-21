package ai.kaira.data.introduction.datasource.network

import ai.kaira.data.introduction.model.UserResponse
import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IntroductionNetworkDataSourceImp @Inject constructor(private val restApiRouter: RestApiRouter, private val viewModelCoroutineScope: CoroutineScope) : IntroductionNetworkDataSource {

    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<Result<User>> {
        val createUserLiveData :  MutableLiveData<Result<User>> = MutableLiveData()

        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.createUser(firstName, languageLocale).execute()
            withContext(Main) {
                if (response.isSuccessful) {
                    val userResponse: UserResponse? = response.body()
                    userResponse?.let {
                        createUserLiveData.value = Result(it.maptoUser(), resultState = ResultState.SUCCESS)
                    }
                } else {
                    val error : String? = response.errorBody()?.string()
                    createUserLiveData.value = error?.let { it1 -> Result(error = it1, resultState = ResultState.ERROR, data = User()) }
                }
            }
        }
        return createUserLiveData
    }


}