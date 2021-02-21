package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Assessment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(val fetchFinancialAssessmentUseCase: FetchFinancialAssessment,val fetchPsychologicalAssessmentUseCase: FetchPsychologicalAssessment) {
    fun fetchFinancialAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchFinancialAssessmentUseCase(locale)
    }

    fun fetchPsychologicalAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchPsychologicalAssessmentUseCase(locale)
    }

}