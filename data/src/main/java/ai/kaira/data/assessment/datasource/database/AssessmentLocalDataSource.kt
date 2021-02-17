package ai.kaira.data.assessment.datasource.database

import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import androidx.lifecycle.MutableLiveData

interface AssessmentLocalDataSource {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
}