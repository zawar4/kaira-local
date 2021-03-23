package ai.kaira.app.assessment.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import ai.kaira.domain.assessment.usecase.FetchPsychologicalAssessmentProfile
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class PsychologicalAssessmentResultViewModel(private val fetchPsychologicalAssessmentProfile: FetchPsychologicalAssessmentProfile) : BaseViewModel() {

    private val psychologicalAssessmentProfileLiveData = MediatorLiveData<PsychologicalProfile>()


    fun fetchPsychologicalAssessmentProfile(){
        val liveDataSource = fetchPsychologicalAssessmentProfile.fetchPsychologicalAssessmentProfileAsync()
        psychologicalAssessmentProfileLiveData.addSource(liveDataSource){
            psychologicalAssessmentProfileLiveData.removeSource(liveDataSource)
            psychologicalAssessmentProfileLiveData.value = it
        }
    }

    fun onPsychologicalAssessmentProfileFetched():MediatorLiveData<PsychologicalProfile>{
        return psychologicalAssessmentProfileLiveData
    }
}