package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(private val assessmentRepository: AssessmentRepository) {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>{
        return assessmentRepository.getFinancialAssessment(locale)
    }

    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>{
        return assessmentRepository.getPsychologicalAssessment(locale)
    }
}