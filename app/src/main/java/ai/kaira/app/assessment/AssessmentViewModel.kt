package ai.kaira.app.assessment

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class AssessmentViewModel(private val assessmentUseCase: AssessmentUseCase) : BaseViewModel() {


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
    private var submittedAnswers : ArrayList<AssessmentAnswer> = ArrayList()
    private var enableSubmitButton : MutableLiveData<Boolean> = MutableLiveData()
    private var questionAttempted : HashSet<Int> = HashSet()
    fun getFinancialAssessment(locale:String):MutableLiveData<Assessment>{
        return assessmentUseCase.getFinancialAssessment(locale)
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
        return assessmentUseCase.getPsychologicalAssessment(locale)
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
                val duration = (currentAnswer!!.time - it)/1000
                Log.v("DURATION",duration.toString())
                currentAnswer?.time = answer.time
                currentAnswer?.duration = duration
            }
        }else if(currentAnswer != null){
            if(currentAnswer!!.id == answer.id){
                val duration : Long = (answer.time - currentAnswer!!.time)/1000
                Log.v("DURATION",duration.toString())
                currentAnswer?.time = answer.time
                currentAnswer?.duration = duration
            }else{
                val duration = (answer.time - currentAnswer!!.time)/1000
                Log.v("DURATION",duration.toString())
                currentAnswer = answer.clone()
                currentAnswer?.time = answer.time
                currentAnswer!!.duration = duration
            }
        }

        questionAttempted.add(currentQuestionNumber)
        //TODO FIRE API CALL
        enableSubmitButton.value = true
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