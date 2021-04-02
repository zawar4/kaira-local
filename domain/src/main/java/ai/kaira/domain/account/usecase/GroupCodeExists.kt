package ai.kaira.domain.account.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.account.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GroupCodeExists @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope):BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(groupCode:String): MutableLiveData<Result<Boolean>> {
        return groupCodeExists(groupCode)
    }

    private fun groupCodeExists(groupCode:String): MutableLiveData<ai.kaira.domain.Result<Boolean>> {
        return accountCreateRepository.groupCodeExists(groupCode)
    }
}