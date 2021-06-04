package ai.kaira.app.application

import ai.kaira.app.account.create.viewmodel.AccountCreateViewModel
import ai.kaira.app.account.login.viewmodel.LoginViewModel
import ai.kaira.app.assessment.viewmodel.AssessmentViewModel
import ai.kaira.app.assessment.viewmodel.FinancialAssessmentResultViewModel
import ai.kaira.app.assessment.viewmodel.PsychologicalAssessmentResultViewModel
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.introduction.IntroductionViewModel
import ai.kaira.app.splash.SplashViewModel
import ai.kaira.domain.account.create.usecase.AccountCreateUseCase
import ai.kaira.domain.account.login.usecase.LoginUseCase
import ai.kaira.domain.assessment.usecase.AssessmentUseCase
import ai.kaira.domain.assessment.usecase.FetchFinancialAssessmentProfile
import ai.kaira.domain.assessment.usecase.FetchPsychologicalAssessmentProfile
import ai.kaira.domain.banking.institution.usecase.InstitutionUseCase
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import ai.kaira.domain.splash.usecase.SplashUseCase
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


    @Inject
    lateinit var fetchFinancialAssessmentProfile: FetchFinancialAssessmentProfile

    @Inject
    lateinit var accountCreateUseCase: AccountCreateUseCase

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Inject
    lateinit var institutionUseCase: InstitutionUseCase

    @Inject
    lateinit var splashUseCase: SplashUseCase

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(IntroductionViewModel::class.java.isAssignableFrom(modelClass)){
            return IntroductionViewModel(introductionUsecase) as T
        }else if(AssessmentViewModel::class.java.isAssignableFrom(modelClass))
            return AssessmentViewModel(assessmentUsecase) as T
        else if(PsychologicalAssessmentResultViewModel::class.java.isAssignableFrom(modelClass))
            return PsychologicalAssessmentResultViewModel(fetchPsychologicalAssessmentProfile) as T
        else if(FinancialAssessmentResultViewModel::class.java.isAssignableFrom(modelClass)){
            return FinancialAssessmentResultViewModel(fetchFinancialAssessmentProfile) as T
        } else if(AccountCreateViewModel::class.java.isAssignableFrom(modelClass)){
            return AccountCreateViewModel(accountCreateUseCase) as T
        }else if(LoginViewModel::class.java.isAssignableFrom(modelClass)){
            return LoginViewModel(loginUseCase) as T
        }else if(InstitutionViewModel::class.java.isAssignableFrom(modelClass)){
            return InstitutionViewModel(institutionUseCase) as T
        }
        else if(SplashViewModel::class.java.isAssignableFrom(modelClass)){
            return SplashViewModel(splashUseCase) as T
        }
        return create(modelClass)
    }
}