package ai.kaira.data.assessment.respository

import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSource
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSource
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentRepositoryImp @Inject constructor(private val assessmentLocalDataSource: AssessmentLocalDataSource,private val assessmentNetworkDataSource: AssessmentNetworkDataSource) : AssessmentRepository {

    override fun getFinancialAssessment(locale: String): MutableLiveData<Assessment> {
        return assessmentLocalDataSource.getFinancialAssessment(locale)
    }

    override fun getPsychologicalAssessment(locale: String): MutableLiveData<Assessment> {
        return assessmentLocalDataSource.getPsychologicalAssessment(locale)
    }

    override fun submitAnswer(answerRequestParam: ai.kaira.domain.assessment.model.AnswerRequestParam): MutableLiveData<Result<Unit>> {
        return assessmentNetworkDataSource.submitAnswer(answerRequestParam)
    }

}