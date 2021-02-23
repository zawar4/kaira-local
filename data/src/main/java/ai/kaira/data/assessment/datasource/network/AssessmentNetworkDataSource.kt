package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.assessment.model.FinancialProfileResponse
import ai.kaira.domain.assessment.model.PsychologicalProfileResponse
import ai.kaira.domain.Result
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<Result<Unit>>
    fun fetchFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<FinancialProfileResponse>>
    fun fetchPsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<PsychologicalProfileResponse>>
}