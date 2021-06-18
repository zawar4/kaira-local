package ai.kaira.data.banking.institution.datasource.network

import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class InstitutionNetworkDataSourceImp @Inject constructor(private val prefs: SharedPreferences, private val kairaApiRouter: KairaApiRouter, private val viewModelCoroutineScope: CoroutineScope) : InstitutionNetworkDataSource {

    override fun verifyInstitutionCode(aggregator: Int, securityAnswer: SecurityAnswer, institutionId: String) = flow<KairaResult<Institution>> {
        emit(KairaResult.loading())
        try {
            val response = kairaApiRouter.submitSecurityAnswer(prefs.getString("token","")!!,aggregator,institutionId,securityAnswer).execute()
            if(response.isSuccessful) {
                val body = response.body()
                emit(KairaResult.success(body))
            } else {
                if(response.code() == 401){
                    val error: String? = response.errorBody()?.string()
                    error?.let{
                        emit(KairaResult.error(message = error,kairaAction = KairaAction.UNAUTHORIZED_REDIRECT))
                    }
                }
                else {
                    val error: String? = response.errorBody()?.string()
                    error?.let{
                        emit(KairaResult.error(message = error))
                    }
                }
            }
        }
        catch (exception : Exception) {
            exception.printStackTrace()
            exception.message?.let { message ->
                emit(KairaResult.exception(message = message))
            }
        }
    }



    override fun connectInstitution(institutionParamBody: InstitutionParamBody): MutableLiveData<KairaResult<ConnectedInstitution>> {
        val connectedInstitutionLiveData = MutableLiveData<KairaResult<ConnectedInstitution>>()
        viewModelCoroutineScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                connectedInstitutionLiveData.value = KairaResult.loading()
            }
            try{
                val response = kairaApiRouter.connectInstitution(prefs.getString("token","")!!,institutionParamBody).execute()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        val body : ConnectedInstitution? = response.body()
                        connectedInstitutionLiveData.value = KairaResult.success(data = body)
                    }else{
                        if(response.code() == 403){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                connectedInstitutionLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNVERIFIED_REDIRECT)
                            }
                        }else if(response.code() == 401){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                connectedInstitutionLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNAUTHORIZED_REDIRECT)
                            }
                        } else {
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                connectedInstitutionLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNKOWN_REDIRECT)
                            }
                        }
                    }
                }
            }
            catch (exception:Exception){
                withContext(Dispatchers.Main) {
                    exception.message?.let { message ->
                        connectedInstitutionLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }

        }
        return connectedInstitutionLiveData
    }

    override fun getMyInstitutions(): MutableLiveData<KairaResult<ArrayList<Institution>>> {
        val myInstitutionsLiveData = MutableLiveData<KairaResult<ArrayList<Institution>>>()
        viewModelCoroutineScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                myInstitutionsLiveData.value = KairaResult.loading()
            }
            try{
                val response = kairaApiRouter.getMyInstitutions(prefs.getString("token","")!!).execute()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        val body : ArrayList<Institution>? = response.body()
                        myInstitutionsLiveData.value = KairaResult.success(data = body)
                    }else{
                        if(response.code() == 403){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                myInstitutionsLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNVERIFIED_REDIRECT)
                            }
                        }else if(response.code() == 401){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                myInstitutionsLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNAUTHORIZED_REDIRECT)
                            }
                        } else {
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                myInstitutionsLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNKOWN_REDIRECT)
                            }
                        }
                    }
                }
            }
            catch (exception:Exception){
                withContext(Dispatchers.Main) {
                    exception.message?.let { message ->
                        myInstitutionsLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }

        }
        return myInstitutionsLiveData
    }
}