package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class BankingInstitutionSyncError(val message:String?,val name : BankingInstitutionSyncErrorType) : Serializable
