package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankingAccountingType(val value:Int) {
    @SerializedName("0")
    assest(0),
    @SerializedName("1")
    liability(1);

    operator fun invoke(type: Int) = BankingAccountingType.values().firstOrNull { it.value == type }

}