package ai.kaira.app.assessment.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.usecase.FetchFinancialAssessmentProfile
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class FinancialAssessmentResultViewModel (private val fetchFinancialAssessmentProfile: FetchFinancialAssessmentProfile) : BaseViewModel() {


    private val financialAssessmentProfileLiveData = MediatorLiveData<FinancialProfile>()


    fun fetchFinancialAssessmentProfile(){
        val liveDataSource = fetchFinancialAssessmentProfile.fetchFinancialAssessmentProfileAsync()
        financialAssessmentProfileLiveData.addSource(liveDataSource){
            financialAssessmentProfileLiveData.removeSource(liveDataSource)
            financialAssessmentProfileLiveData.value = it
        }
    }

    fun onFinancialAssessmentProfileFetched(): MediatorLiveData<FinancialProfile> {
        return financialAssessmentProfileLiveData
    }
}