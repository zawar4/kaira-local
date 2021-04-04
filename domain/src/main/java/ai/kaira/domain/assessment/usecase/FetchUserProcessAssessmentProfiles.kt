package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.introduction.usecase.FetchUser
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUserProcessAssessmentProfiles @Inject constructor(private val fetchUser: FetchUser, private val processAssessmentProfiles: ProcessAssessmentProfiles,private val fetchPsychologicalAssessmentProfile: FetchPsychologicalAssessmentProfile,private val fetchFinancialAssessmentProfile: FetchFinancialAssessmentProfile, private val viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(languageLocale:String): MediatorLiveData<KairaResult<Strategy>> {
        return fetchUserProcessAssessmentProfiles(languageLocale)
    }

    private fun fetchUserProcessAssessmentProfiles(languageLocale:String):MediatorLiveData<KairaResult<Strategy>>{
        val processAssessmentProfilesLiveData = MediatorLiveData<KairaResult<Strategy>>()
        viewModelCoroutineScope.launch(IO){
            val user = fetchUser()
            val financialAssessmentProfile = fetchFinancialAssessmentProfile.fetchFinancialAssessmentProfileSync()
            val psychologicalAssessmentProfile = fetchPsychologicalAssessmentProfile.fetchPsychologicalAssessmentProfileSync()
            if(user != null && financialAssessmentProfile != null && psychologicalAssessmentProfile!= null){

                withContext(Main){
                    val liveDataSource = processAssessmentProfiles(languageLocale,user.id,financialAssessmentProfile!!,psychologicalAssessmentProfile!!)
                    processAssessmentProfilesLiveData.addSource(liveDataSource){ result ->
                        when(result.status){
                            ResultState.SUCCESS->{
                                processAssessmentProfilesLiveData.value = result
                                processAssessmentProfilesLiveData.removeSource(liveDataSource)
                            }
                            ResultState.ERROR->{
                                processAssessmentProfilesLiveData.value = result
                                processAssessmentProfilesLiveData.removeSource(liveDataSource)
                            }
                            ResultState.EXCEPTION->{
                                processAssessmentProfilesLiveData.value = result
                                processAssessmentProfilesLiveData.removeSource(liveDataSource)
                            }
                            ResultState.LOADING->{
                                processAssessmentProfilesLiveData.value = result
                            }
                        }
                    }
                }
            }
        }
        return processAssessmentProfilesLiveData
    }


}