package ai.kaira.data.assessment.respository

import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSource
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSource
import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.respository.AssessmentRepository
import ai.kaira.domain.introduction.model.User
import ai.kaira.data.utils.UtilityFunctions
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentRepositoryImp @Inject constructor(private val assessmentLocalDataSource: AssessmentLocalDataSource,private val assessmentNetworkDataSource: AssessmentNetworkDataSource) : AssessmentRepository {

    override fun getFinancialAssessment(locale: String): MutableLiveData<Assessment> {
        return assessmentLocalDataSource.getFinancialAssessment(locale)
    }

    override fun getPsychologicalAssessment(locale: String): MutableLiveData<Assessment> {
        return assessmentLocalDataSource.getPsychologicalAssessment(locale)
    }


    override fun submitAssessmentAnswer(user: User, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<Result<Unit>> {
        var answeredAt = UtilityFunctions.iso8601FormatDate(answer!!.time.toLong())
        var assessmentAnswerRequestParam = AssessmentAnswerRequestParam(user.id,
                assessment.id,
                assessment.version,
                assessment.type,
                question.id,
                question.type,
                answer.id,
                answer.value,
                answeredAt,
                answer.duration)
        return assessmentNetworkDataSource.submitAssessmentAnswer(assessmentAnswerRequestParam)
    }

    override fun isQuestionAlreadyAnswered(assessmentId: Int, assessmentType: Int, questionId: Int): Int {
        return assessmentLocalDataSource.isQuestionAlreadyAnswered(assessmentId,assessmentType,questionId)
    }

    override fun saveSelectedAssessmentAnswer(assessmentId: Int, assessmentType: Int, questionId: Int, assessmentAnswerPosition: Int) {
        assessmentLocalDataSource.saveSelectedAssessmentAnswer(assessmentId,assessmentType,questionId,assessmentAnswerPosition)
    }

    override fun deleteUserOldAssessmentsAnswers() {
        assessmentLocalDataSource.deleteUserOldAssessmentsAnswers()
    }

    override fun markAssessmentAsComplete(assessmentType: Int){
        assessmentLocalDataSource.markAssessmentAsComplete(assessmentType)
    }

    override fun isAssessmentCompleted(assessmentType: Int): Boolean {
        return assessmentLocalDataSource.isAssessmentCompleted(assessmentType)
    }

}