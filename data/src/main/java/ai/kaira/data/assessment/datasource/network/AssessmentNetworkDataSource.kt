package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.Result
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<Result<Unit>>
    fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<FinancialProfile>>
    fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<PsychologicalProfile>>
}