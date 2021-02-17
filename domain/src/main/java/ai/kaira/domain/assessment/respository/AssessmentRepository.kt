package ai.kaira.domain.assessment.respository

import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import androidx.lifecycle.MutableLiveData

interface AssessmentRepository {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
}