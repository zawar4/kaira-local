package ai.kaira.app.assessment

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import androidx.lifecycle.MutableLiveData

class AssessmentViewModel(private val assessmentUseCase: AssessmentUseCase) : BaseViewModel() {

    fun getFinancialAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.getFinancialAssessment(locale)
    }

    fun getPsychologicalAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.getPsychologicalAssessment(locale)
    }
}