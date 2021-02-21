package ai.kaira.app.assessment

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.*
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import ai.kaira.domain.assessment.usecase.FetchUserSubmitAssessmentAnswer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.HashSet

class AssessmentViewModel(private val assessmentUseCase: AssessmentUseCase,private val fetchUserSubmitAssessmentAnswer: FetchUserSubmitAssessmentAnswer) : BaseViewModel() {


    lateinit var assessment: Assessment
    private var currentQuestionNumber : Int = -1
    private var screenVisibleTime : Long? = null
    private var currentAnswer: AssessmentAnswer? = null
    private var currentAnswerPosition : Int = -1
    private lateinit var currentQuestion : AssessmentQuestion

    private var financialAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var psychologicalAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var currentQuestionAnswers : MutableLiveData<List<AssessmentAnswer>> = MutableLiveData()
    private var questionNumber : MutableLiveData<String> = MutableLiveData()
    private var questionTitle : MutableLiveData<String> = MutableLiveData()
    private var enableSubmitButton : MutableLiveData<Boolean> = MutableLiveData()
    var submitAssessmentAnswerLiveData = MediatorLiveData<Result<Unit>>()

    fun getFinancialAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.fetchFinancialAssessment(locale)
    }
    private fun fetchFinancialAssessment(locale:String) : MediatorLiveData<Assessment>{
        val financialAssessmentLivaDataSource = getFinancialAssessment(locale)
        financialAssessmentLiveData.addSource(financialAssessmentLivaDataSource){
            assessment = it
            financialAssessmentLiveData.value = assessment
            financialAssessmentLiveData.removeSource(financialAssessmentLivaDataSource)
        }
        return financialAssessmentLiveData
    }

    fun fetchAssessments(assessmentType:AssessmentType,languageLocale:String):MutableLiveData<Assessment> {
        return when (assessmentType) {
            AssessmentType.FINANCIAL -> {
                fetchFinancialAssessment(languageLocale)
            }
            AssessmentType.PSYCHOLOGICAL -> {
                fetchPsychologicalAssessment(languageLocale)
            }
        }
    }

    fun getPsychologicalAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.fetchPsychologicalAssessment(locale)
    }

    private fun fetchPsychologicalAssessment(locale:String) : MediatorLiveData<Assessment>{
        val psychologicalAssessmentLivaDataSource = getPsychologicalAssessment(locale)
        psychologicalAssessmentLiveData.addSource(psychologicalAssessmentLivaDataSource){
            assessment = it
            psychologicalAssessmentLiveData.value = assessment
            psychologicalAssessmentLiveData.removeSource(psychologicalAssessmentLivaDataSource)
        }
        return psychologicalAssessmentLiveData
    }

    fun setQuestionNumber():MutableLiveData<String>{
        return questionNumber
    }

    fun setQuestionTitle(): MutableLiveData<String>{
        return questionTitle
    }
    fun setQuestionAnswers():MutableLiveData<List<AssessmentAnswer>>{
        return currentQuestionAnswers
    }

    fun setQuestionAnswered(assessmentAnswerClick: AssessmentAnswerClick){

        val newAnswer :AssessmentAnswer = currentQuestion.answers[assessmentAnswerClick.position]
        if(currentAnswerPosition > -1){
            currentQuestion.answers[currentAnswerPosition].selected = false
        }
        currentAnswer = assessmentUseCase.onAssessmentQuestionAnswered(screenVisibleTime?.toDouble()!!,assessmentAnswerClick, currentAnswer,newAnswer.clone())
        currentAnswer?.let {
            currentAnswerPosition = assessmentAnswerClick.position
            it.selected = true
            currentQuestion.answers[currentAnswerPosition] = it
            saveSelectedAnswer(assessment.id,assessment.type,currentQuestion.id,currentAnswerPosition)
            reloadCurrentQuestion()
            submitAssessmentAnswer()
        }
    }

    private fun submitAssessmentAnswer(){
        if(!isConnectedToInternet()){
            showConnectivityError()
        }else{
            enableSubmitButton.value = true
            currentAnswer?.let{
                val liveDataSource : MutableLiveData<ai.kaira.domain.Result<Unit>>  = fetchUserSubmitAssessmentAnswer(currentQuestion,it,assessment)
                submitAssessmentAnswerLiveData.addSource(liveDataSource) {
                    val result: ai.kaira.domain.Result<Unit>? = it
                    when(result?.resultState){
                        ResultState.SUCCESS -> {
                            // Do nothing
                            submitAssessmentAnswerLiveData.removeSource(liveDataSource)
                        }
                        ResultState.ERROR ->{
                            showError(result.error)
                            submitAssessmentAnswerLiveData.removeSource(liveDataSource)
                        }
                        else ->  submitAssessmentAnswerLiveData.removeSource(liveDataSource)
                    }
                }
            }
        }
    }

    fun loadFirstQuestion(){
        screenVisibleTime = Calendar.getInstance().timeInMillis
        loadNextQuestion()
    }

    fun loadPreviousQuestion(){
        if(!isFirstQuestion())
        {
            screenVisibleTime = Calendar.getInstance().timeInMillis
            currentAnswer = null
            currentQuestion = assessment.questions[--currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            populatePreselectedAnswers(assessment.id,assessment.type,currentQuestion.id)
            currentQuestionAnswers.value = currentQuestion.answers
        }

    }

    private fun reloadCurrentQuestion(){
        populatePreselectedAnswers(assessment.id,assessment.type,currentQuestion.id)
        currentQuestionAnswers.value = currentQuestion.answers
    }

    fun loadNextQuestion(){
        if(!isLastQuestion()){
            screenVisibleTime = Calendar.getInstance().timeInMillis
            currentAnswer = null
            currentQuestion = assessment.questions[++currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            populatePreselectedAnswers(assessment.id,assessment.type,currentQuestion.id)
            currentQuestionAnswers.value = currentQuestion.answers
        }else{

        }

    }

    private fun saveSelectedAnswer(assessmentId:Int,assessmentType: Int,questionId:Int,assessmentAnswerPosition:Int){
        assessmentUseCase.saveSelectedAssessmentAnswer(assessmentId,assessmentType,questionId,assessmentAnswerPosition)
    }
    private fun populatePreselectedAnswers(assessmentId:Int,assessmentType: Int,questionId:Int){
        val assessmentAnswerPosition : Int = assessmentUseCase.isQuestionAlreadyAnswered(assessmentId,assessmentType,questionId);
        if(assessmentAnswerPosition > -1){
            currentAnswerPosition = assessmentAnswerPosition
            currentQuestion.answers[assessmentAnswerPosition].selected = true
            enableSubmitButton.value = true
        }else{
            enableSubmitButton.value = false
        }

    }

    fun setSubmitButton(): MutableLiveData<Boolean>{
        return enableSubmitButton
    }
    fun isFirstQuestion(): Boolean{
        return currentQuestionNumber == 0
    }

    private fun isLastQuestion():Boolean{
        return currentQuestionNumber == assessment.questions.size -1
    }
}