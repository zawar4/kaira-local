package ai.kaira.domain.account.create.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GroupCodeExists @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope):BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(groupCode:String): MutableLiveData<KairaResult<Boolean>> {
        return groupCodeExists(groupCode)
    }

    private fun groupCodeExists(groupCode:String): MutableLiveData<ai.kaira.domain.KairaResult<Boolean>> {
        return accountCreateRepository.groupCodeExists(groupCode)
    }
}