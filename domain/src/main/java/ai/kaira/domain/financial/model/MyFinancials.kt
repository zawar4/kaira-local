package ai.kaira.domain.financial.model

import ai.kaira.domain.banking.institution.model.Institution
import androidx.annotation.Keep

@Keep
data class MyFinancials(val revenue : ArrayList<Revenue>,
                        val spending : ArrayList<Spending>,
                        val leeway : ArrayList<Leeway>,
                        val institutions : ArrayList<Institution>,
                        val balanceSheet : BalanceSheet)
