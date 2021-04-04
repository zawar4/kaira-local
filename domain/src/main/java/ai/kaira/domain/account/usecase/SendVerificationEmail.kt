package ai.kaira.domain.account.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.account.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SendVerificationEmail @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope): BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(email:String):MutableLiveData<Result<Void>>{
        return sendVerificationEmail(email)
    }

    private fun sendVerificationEmail(email:String): MutableLiveData<ai.kaira.domain.Result<Void>> {
        return accountCreateRepository.sendVerificationEmail(email)
    }
}