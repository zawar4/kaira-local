package ai.kaira.domain.banking.institution.model

import java.io.Serializable

import androidx.annotation.Keep
@Keep
data class InstitutionParamBody(val type:BankingAggregator,val institution: InstitutionParam) : Serializable
