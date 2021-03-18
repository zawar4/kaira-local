package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.data.assessment.model.ProcessAssessmentAnswersParam
import ai.kaira.data.assessment.model.StrategyResponse
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Strategy
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<Result<Unit>>
    fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<FinancialProfile>>
    fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<PsychologicalProfile>>
    fun processAssessmentProfiles(processAssessmentAnswersParam: ProcessAssessmentAnswersParam,languageLocale: String) : MutableLiveData<Result<Strategy>>
}