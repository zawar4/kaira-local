package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankingInstitutionSyncStatus(val status:String) {
    @SerializedName("ok")
    OK("ok"),
    @SerializedName("error")
    ERROR("error"),
    @SerializedName("syncing")
    SYNCING("syncing"),
    @SerializedName("retry")
    RETRY("retry");

    operator fun invoke(status:String) = values().firstOrNull { status == it.status }
}