package ai.kaira.domain.account.create.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.repository.AccountCreateRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SendVerificationEmail @Inject constructor(private val accountCreateRepository: AccountCreateRepository, viewModelCoroutineScope: CoroutineScope): BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(email:String,token:String):MutableLiveData<KairaResult<Void>>{
        return sendVerificationEmail(email,token)
    }

    private fun sendVerificationEmail(email:String,token:String): MutableLiveData<KairaResult<Void>> {
        return accountCreateRepository.sendVerificationEmail(email,token)
    }
}