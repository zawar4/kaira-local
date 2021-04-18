package ai.kaira.domain.account.create.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.model.Account
import ai.kaira.domain.account.create.repository.AccountCreateRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CreateAccount @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope): BaseUseCase(viewModelCoroutineScope)  {
    operator fun invoke (accountDetails:Account):MutableLiveData<KairaResult<User>> {
        return createAccount(accountDetails)
    }

    fun createAccount(accountDetails:Account):MutableLiveData<KairaResult<User>>{
        return accountCreateRepository.createAccount(accountDetails)
    }

}