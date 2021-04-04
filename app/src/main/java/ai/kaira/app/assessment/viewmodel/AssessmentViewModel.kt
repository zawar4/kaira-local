package ai.kaira.app.assessment.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.app.utils.Extensions.Companion.isConnectedToInternet
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.*
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class AssessmentViewModel(private val assessmentUseCase: AssessmentUseCase) : BaseViewModel() {


    lateinit var assessment: Assessment
    private var currentQuestionNumber : Int = -1
    private var screenVisibleTime : Long? = null
    private var currentAnswer: AssessmentAnswer? = null
    private var currentAnswerPosition : Int = -1
    private lateinit var currentQuestion : AssessmentQuestion

    private var financialAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var psychologicalAssessmentLiveData : MediatorLiveData<Assessment> = MediatorLiveData()
    private var currentQuestionAnswers : MutableLiveData<List<AssessmentAnswer>> = MutableLiveData()
    private var nextQuestionAnswers : MutableLiveData<List<AssessmentAnswer>> = MutableLiveData()
    private var questionNumber : MutableLiveData<String> = MutableLiveData()
    private var questionTitle : MutableLiveData<String> = MutableLiveData()
    private var enableSubmitButton : MutableLiveData<Boolean> = MutableLiveData()
    var submitAssessmentAnswerLiveData = MediatorLiveData<String>()

    var financialAssessmentProfileComputeLiveData = MediatorLiveData<FinancialProfile>()
    var psychologicalAssessmentProfileComputeLiveData = MediatorLiveData<PsychologicalProfile>()

    var startComputeAssessmentProfileLiveData = MutableLiveData<AssessmentType>()

    var strategyLiveData = MediatorLiveData<Strategy?>()

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
    fun setNextQuestionAnswers():MutableLiveData<List<AssessmentAnswer>>{
        return nextQuestionAnswers
    }

    fun setCurrentQuestionAnswers():MutableLiveData<List<AssessmentAnswer>>{
        return currentQuestionAnswers
    }

    fun setQuestionAnswered(assessmentAnswerClick: AssessmentAnswerClick){

        if(!isConnectedToInternet()){
            showConnectivityError()
            rollBackAssessmentAnswerClick()
        }else{
            val newAnswer :AssessmentAnswer = currentQuestion.answers[assessmentAnswerClick.position]
            if(currentAnswerPosition > -1){
                currentQuestion.answers[currentAnswerPosition].selected = false
            }
            currentAnswer = assessmentUseCase.onAssessmentQuestionAnswered(screenVisibleTime?.toDouble()!!,assessmentAnswerClick, currentAnswer,newAnswer.clone())
            currentAnswer?.let {
                currentAnswerPosition = assessmentAnswerClick.position
                it.selected = true
                currentQuestion.answers[currentAnswerPosition] = it
                saveSelectedAnswer(assessment.id,assessment.type.value,currentQuestion.id,currentAnswerPosition)
                reloadCurrentQuestion()
                submitAssessmentAnswer()
            }
        }

    }

    fun computeFinancialAssessmentProfile(assessmentType: Int){
        if(isConnectedToInternet()){
            val liveDataSource = assessmentUseCase.computeFinancialAssessmentProfile(assessmentType)
            financialAssessmentProfileComputeLiveData.addSource(liveDataSource){
                it?.let{
                    when(it.status){
                        ResultState.SUCCESS ->{
                            showLoading(false)
                            it.data?.let {it->
                                assessmentUseCase.saveFinancialAssessmentProfile(it)
                            }
                            financialAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.ERROR ->{
                            it.message?.let { it2->
                                showError(it2)
                                showLoading(false)
                            }
                            financialAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.EXCEPTION ->{
                            it.message?.let { it2->
                                showError(it2)
                                showLoading(false)
                            }
                            financialAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.LOADING->{
                            showLoading(true)
                        }
                    }
                }
            }
        }else{
            showConnectivityError()
        }

    }

    fun onFinancialAssessmentProfileComputed():MediatorLiveData<FinancialProfile>{
        return financialAssessmentProfileComputeLiveData
    }

    fun onPsychologicalAssessmentProfileComputed():MediatorLiveData<PsychologicalProfile>{
        return psychologicalAssessmentProfileComputeLiveData
    }

    fun computePsychologicalAssessmentProfile(assessmentType: Int) {
        if(isConnectedToInternet()){
            val liveDataSource = assessmentUseCase.computePsychologicalAssessmentProfile(assessmentType)
            psychologicalAssessmentProfileComputeLiveData.addSource(liveDataSource){it ->
                it?.let{
                    when(it.status){
                        ResultState.SUCCESS ->{
                            showLoading(false)
                            it.data?.let {it2->
                                assessmentUseCase.savePsychologicalAssessmentProfile(it2)
                            }
                            psychologicalAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.ERROR ->{
                            it.message?.let { it2->
                                showError(it2)
                                showLoading(false)
                            }
                            psychologicalAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.EXCEPTION ->{
                            it.message?.let { it2->
                                showError(it2)
                                showLoading(false)
                            }
                            psychologicalAssessmentProfileComputeLiveData.removeSource(liveDataSource)
                        }
                        ResultState.LOADING->{
                            showLoading(true)
                        }
                    }
                }
            }
        }else{
            showConnectivityError()
        }

    }

    private fun rollBackAssessmentAnswerClick(){
        reloadCurrentQuestion()
    }

    private fun submitAssessmentAnswer(){
        if(!isConnectedToInternet()){
            showConnectivityError()
        }else{
            enableSubmitButton.value = true
            currentAnswer?.let{
                val liveDataSource : MediatorLiveData<ai.kaira.domain.KairaResult<Unit>>  = assessmentUseCase.submitAssessmentAnswer(currentQuestion,it,assessment)
                submitAssessmentAnswerLiveData.addSource(liveDataSource) { it2 ->
                    val result: ai.kaira.domain.KairaResult<Unit>? = it2
                    when(result?.status){
                        ResultState.SUCCESS -> {
                            // Do nothing
                            submitAssessmentAnswerLiveData.removeSource(liveDataSource)
                        }
                        ResultState.ERROR ->{
                            result.message?.let {it ->
                                showError(it)
                            }
                            submitAssessmentAnswerLiveData.removeSource(liveDataSource)
                        }
                        ResultState.EXCEPTION ->{
                            result.message?.let {it ->
                                showError(it)
                            }
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
            currentAnswerPosition = -1
            currentQuestion = assessment.questions[--currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            populatePreselectedAnswers(assessment.id,assessment.type.value,currentQuestion.id)
            nextQuestionAnswers.value = currentQuestion.answers
        }

    }

    private fun reloadCurrentQuestion(){
        populatePreselectedAnswers(assessment.id,assessment.type.value,currentQuestion.id)
        currentQuestionAnswers.value = currentQuestion.answers
    }

    fun loadNextQuestion(){
        if(!isLastQuestion()){
            screenVisibleTime = Calendar.getInstance().timeInMillis
            currentAnswer = null
            currentAnswerPosition = -1
            currentQuestion = assessment.questions[++currentQuestionNumber]
            questionNumber.value = "${currentQuestionNumber+1} / ${assessment.questions.size}"
            questionTitle.value = currentQuestion.title
            populatePreselectedAnswers(assessment.id,assessment.type.value,currentQuestion.id)
            nextQuestionAnswers.value = currentQuestion.answers
        }else{
            startComputeAssessmentProfileActivity()

        }

    }

   /* private fun markAssessmentAsComplete(assessmentType: Int){
        assessmentUseCase.markAssessmentAsComplete(assessmentType)
    }

    */
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

    private fun startComputeAssessmentProfileActivity(){
        if(AssessmentType.PSYCHOLOGICAL == assessment.type){
            startComputeAssessmentProfileLiveData.value = AssessmentType.PSYCHOLOGICAL
        }else if(AssessmentType.FINANCIAL == assessment.type){
            startComputeAssessmentProfileLiveData.value = AssessmentType.FINANCIAL
        }

    }


    fun onStrategyFetch(): MutableLiveData<Strategy?>{
        return strategyLiveData
    }
    fun fetchStrategy(){
        if(isConnectedToInternet()){
            val liveDataSource = assessmentUseCase.fetchStrategy()
            strategyLiveData.addSource(liveDataSource){
                it?.let{
                    strategyLiveData.value = it
                }
                strategyLiveData.removeSource(liveDataSource)
            }
        }else{
            showConnectivityError()
        }
    }

    fun onStartComputeAssessmentProfileActivity():MutableLiveData<AssessmentType>{
        return startComputeAssessmentProfileLiveData
    }

    fun onSubmitAnswer(): MediatorLiveData<String>{
        return submitAssessmentAnswerLiveData
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