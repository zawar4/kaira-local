package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.FinancialProfileResponse
import ai.kaira.domain.assessment.model.PsychologicalProfileResponse
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.FetchUser
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUserComputeAssessmentProfile @Inject constructor(private val fetchUser: FetchUser,private val computePsychologicalAssessmentProfile: ComputePsychologicalAssessmentProfile,private val computeFinancialAssessmentProfile: ComputeFinancialAssessmentProfile,private val viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {



    fun computePsychologicalAssessmentProfile(assessmentType: Int) : MediatorLiveData<Result<PsychologicalProfileResponse>> {
        val computePsychologicalProfileLiveData = MediatorLiveData<Result<PsychologicalProfileResponse>>()
        viewModelCoroutineScope.launch(IO) {
            val user : User = fetchUser()
            withContext(Dispatchers.Main){
                user.let{ it ->
                    val liveDataSource = computePsychologicalAssessmentProfile(assessmentType,it.id)
                    computePsychologicalProfileLiveData.addSource(liveDataSource){it->
                        computePsychologicalProfileLiveData.value = it
                        computePsychologicalProfileLiveData.removeSource(liveDataSource)
                    }
                }

            }
        }
        return computePsychologicalProfileLiveData
    }

    fun computeFinancialAssessmentProfile(assessmentType: Int) : MediatorLiveData<Result<FinancialProfileResponse>> {
        val computeFinancialProfileLiveData = MediatorLiveData<Result<FinancialProfileResponse>>()
        viewModelCoroutineScope.launch(IO) {
            val user : User = fetchUser()
            withContext(Main){
                user?.let{ it ->
                    val liveDataSource = computeFinancialAssessmentProfile(assessmentType,it.id)
                    computeFinancialProfileLiveData.addSource(liveDataSource){it2 ->
                        computeFinancialProfileLiveData.value = it2
                        computeFinancialProfileLiveData.removeSource(liveDataSource)
                    }
                }

            }
        }
        return computeFinancialProfileLiveData
    }
}