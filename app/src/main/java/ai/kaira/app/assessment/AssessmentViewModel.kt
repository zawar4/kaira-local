package ai.kaira.app.assessment

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import ai.kaira.domain.assessment.usecase.FetchUserSubmitAssessmentAnswer
import ai.kaira.domain.introduction.model.User
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.HashSet

class AssessmentViewModel(private val assessmentUseCase: AssessmentUseCase,private val fetchUserSubmitAssessmentAnswer: FetchUserSubmitAssessmentAnswer) : BaseViewModel() {


    lateinit var assessment: Assessment
    private var currentQuestionNumber : Int = -1
    private lateinit var currentQuestion : AssessmentQuestion
    private var financialAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var psychologicalAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var currentQuestionAnswers : MutableLiveData<List<AssessmentAnswer>> = MutableLiveData()
    private var questionNumber : MutableLiveData<String> = MutableLiveData()
    private var questionTitle : MutableLiveData<String> = MutableLiveData()
    private var clickTime : Long? = null
    private var currentAnswer: AssessmentAnswer? = null
    private var enableSubmitButton : MutableLiveData<Boolean> = MutableLiveData()
    private var questionAttempted : HashSet<Int> = HashSet()
    var submitAssessmentAnswer = MediatorLiveData<Result<Unit>>()

    fun getFinancialAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.fetchFinancialAssessment(locale)
    }
    fun fetchFinancialAssessment(locale:String) : MediatorLiveData<Assessment>{
        val financialAssessmentLivaDataSource = getFinancialAssessment(locale)
        financialAssessmentLiveData.addSource(financialAssessmentLivaDataSource){
            assessment = it
            financialAssessmentLiveData.value = assessment
            financialAssessmentLiveData.removeSource(financialAssessmentLivaDataSource)
        }
        return financialAssessmentLiveData
    }

    fun getPsychologicalAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.fetchPsychologicalAssessment(locale)
    }

    fun fetchPsychologicalAssessment(locale:String) : MediatorLiveData<Assessment>{
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

    fun setQuestionAnswered(answer:AssessmentAnswer){
        if(clickTime != null && currentAnswer == null){
            clickTime?.let {
                currentAnswer = answer.clone()
                val duration : Double = (currentAnswer!!.time - it)/1000.00
                Log.v("DURATION",duration.toString())
                currentAnswer?.time = answer.time
                currentAnswer?.duration = duration
            }
        }else if(currentAnswer != null){
            if(currentAnswer!!.id == answer.id){
                val duration : Double = (answer.time - currentAnswer!!.time)/1000.00
                Log.v("DURATION",duration.toString())
                currentAnswer?.time = answer.time
                currentAnswer?.duration = duration
            }else{
                val duration = (answer.time - currentAnswer!!.time)/1000.00
                Log.v("DURATION",duration.toString())
                currentAnswer = answer.clone()
                currentAnswer?.time = answer.time
                currentAnswer!!.duration = duration
            }
        }

        submitAssessmentAnswer()

    }

    private fun submitAssessmentAnswer(){
        if(!isConnectedToInternet()){
            showConnectivityError()
        }else{
            questionAttempted.add(currentQuestionNumber)
            enableSubmitButton.value = true
            currentAnswer?.let{
                val liveData : MutableLiveData<ai.kaira.domain.Result<Unit>>  = fetchUserSubmitAssessmentAnswer(currentQuestion,it,assessment)
                submitAssessmentAnswer.addSource(liveData) {
                    val result: ai.kaira.domain.Result<Unit>? = it
                    when(result?.resultState){
                        ResultState.SUCCESS -> {
                            // Do nothing
                        }
                        ResultState.ERROR ->{
                            showError(result.error)
                        }
                    }
                }
            }
        }
    }

    fun loadFirstQuestion(){
        clickTime = Calendar.getInstance().timeInMillis
        loadNextQuestion()
    }

    fun loadPreviousQuestion(){
        if(!isFirstQuestion())
        {
            clickTime = Calendar.getInstance().timeInMillis
            currentAnswer = null
            currentQuestion = assessment.questions[--currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            currentQuestionAnswers.value = currentQuestion.answers
            enableSubmitButton.value = questionAttempted.contains(currentQuestionNumber)
        }

    }

    fun loadNextQuestion(){
        if(!isLastQuestion()){
            clickTime = Calendar.getInstance().timeInMillis
            currentAnswer = null
            currentQuestion = assessment.questions[++currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            currentQuestionAnswers.value = currentQuestion.answers
            enableSubmitButton.value = questionAttempted.contains(currentQuestionNumber)

        }else{

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