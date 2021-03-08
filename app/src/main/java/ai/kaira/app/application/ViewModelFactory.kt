package ai.kaira.app.application

import ai.kaira.app.assessment.AssessmentViewModel
import ai.kaira.app.assessment.PsychologicalAssessmentResultViewModel
import ai.kaira.app.introduction.IntroductionViewModel
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import ai.kaira.domain.assessment.usecase.FetchPsychologicalAssessmentProfile
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    @Inject
    lateinit var introductionUsecase: IntroductionUsecase

    @Inject
    lateinit var assessmentUsecase: AssessmentUseCase


    @Inject
    lateinit var fetchPsychologicalAssessmentProfile: FetchPsychologicalAssessmentProfile

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(IntroductionViewModel::class.java.isAssignableFrom(modelClass)){
            return IntroductionViewModel(introductionUsecase) as T
        }else if(AssessmentViewModel::class.java.isAssignableFrom(modelClass))
            return AssessmentViewModel(assessmentUsecase) as T
        else if(PsychologicalAssessmentResultViewModel::class.java.isAssignableFrom(modelClass))
            return PsychologicalAssessmentResultViewModel(fetchPsychologicalAssessmentProfile) as T
        return create(modelClass)
    }
}