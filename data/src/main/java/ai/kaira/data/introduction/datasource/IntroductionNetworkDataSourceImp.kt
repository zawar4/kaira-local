package ai.kaira.data.introduction.datasource

import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.data.introduction.dto.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntroductionNetworkDataSourceImp @Inject constructor(val restApiRouter: RestApiRouter) : IntroductionNetworkDataSource {


    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<User> {
        val createUserLiveData :  MutableLiveData<User> = MutableLiveData()

        GlobalScope.launch(IO) {
            val response = restApiRouter.createUser(firstName,languageLocale).execute()
            if(response?.isSuccessful == true){
                GlobalScope.launch (Main) {
                    val user = response.body()
                    createUserLiveData.value = user
                }
            }else{

            }
        }

        return createUserLiveData

    }
}