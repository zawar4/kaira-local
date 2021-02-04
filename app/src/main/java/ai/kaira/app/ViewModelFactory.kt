package ai.kaira.app

import ai.kaira.app.introduction.IntroductionViewModel
import ai.kaira.domain.introduction.usecase.CreateUserUsecase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    @Inject
    lateinit var createUserUsecase: CreateUserUsecase

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(IntroductionViewModel::class.java.isAssignableFrom(modelClass)){
            return IntroductionViewModel(createUserUsecase) as T
        }
        return create(modelClass)
    }
}