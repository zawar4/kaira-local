package ai.kaira.domain.account.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.account.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class EmailExists @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope): BaseUseCase(viewModelCoroutineScope)  {
    operator fun invoke(email:String): MutableLiveData<Result<Boolean>> {
        return emailExists(email)
    }

    private fun emailExists(email:String): MutableLiveData<Result<Boolean>> {
        return accountCreateRepository.emailExists(email)
    }
}