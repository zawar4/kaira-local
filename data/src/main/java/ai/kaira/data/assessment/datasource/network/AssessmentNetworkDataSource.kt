package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.data.assessment.model.ProcessAssessmentAnswersParam
import ai.kaira.data.assessment.model.StrategyResponse
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.Strategy
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<KairaResult<Unit>>
    fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<FinancialProfile>>
    fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<PsychologicalProfile>>
    fun processAssessmentProfiles(processAssessmentAnswersParam: ProcessAssessmentAnswersParam,languageLocale: String) : MutableLiveData<KairaResult<Strategy>>
}