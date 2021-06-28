package ai.kaira.domain.banking.institution.model

import ai.kaira.domain.R
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankAccountType(val type:String) {

    @SerializedName("Deposit_account")
    deposit("Deposit_account"),
    @SerializedName("Homeloan_account")
    mortgage("Homeloan_account"),
    @SerializedName("Investment_retirement")
    retirement("Investment_retirement"),
    @SerializedName("Card_account")
    creditCard("Card_account"),
    @SerializedName("Savings_account")
    saving("Savings_account"),
    @SerializedName("Loan_account")
    loan("Loan_account"),
    @SerializedName("Investment_account")
    investment("Investment_account"),
    @SerializedName("Others_registered_account")
    otherRegisteredAccount("Others_registered_account"),
    @SerializedName("Others")
    other("Others");

    operator fun invoke(type:String) = values().firstOrNull{ it.type == type }


    fun isInvestment() : Boolean {
        if(this == deposit || this == creditCard || this == saving || this == other) {
            return false
        } else if(this == mortgage || this == loan || this == investment || this == otherRegisteredAccount || this == retirement) {
            return true
        }
        return false
    }

    companion object {

    }
}