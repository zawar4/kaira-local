package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep

@Keep
data class BankingInstitutionSyncError(val message:String?,val name : BankingInstitutionSyncErrorType)
