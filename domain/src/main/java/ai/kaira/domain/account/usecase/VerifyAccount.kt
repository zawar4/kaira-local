package ai.kaira.domain.account.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class VerifyAccount@Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope): BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(token:String):MutableLiveData<KairaResult<Void>>{
        return verifyAccount(token)
    }
    private fun verifyAccount(token:String):MutableLiveData<KairaResult<Void>>{
        return accountCreateRepository.verifyAccount(token)
    }
}