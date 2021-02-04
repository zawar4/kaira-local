package ai.kaira.data.introduction.datasource

import ai.kaira.domain.Result
import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.data.introduction.dto.User
import ai.kaira.domain.ResultState
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IntroductionNetworkDataSourceImp @Inject constructor(val restApiRouter: RestApiRouter) : IntroductionNetworkDataSource {

    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<Result<User>> {
        val createUserLiveData :  MutableLiveData<Result<User>> = MutableLiveData()

        createUserLiveData.value = Result(resultState = ResultState.LOADING, data = User())
        GlobalScope.launch(IO) {
            val response = restApiRouter.createUser(firstName,languageLocale).execute()
            Thread.sleep(400)
            withContext (Main) {
                if (response?.isSuccessful == true) {
                    val user: User? = response.body()
                    user?.let {
                        createUserLiveData.value = Result(user, resultState = ResultState.SUCCESS)
                    }
                } else {
                    createUserLiveData.value = Result(error = "An Error Occurred", resultState = ResultState.ERROR, data = User());
                }
            }
        }
        return createUserLiveData

    }
}