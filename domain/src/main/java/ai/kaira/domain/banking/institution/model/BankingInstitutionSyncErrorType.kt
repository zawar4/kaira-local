package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankingInstitutionSyncErrorType (val type : String) {
    @SerializedName("LoginFailedError")
    LOGIN_FAILED_ERROR("LoginFailedError"),
    @SerializedName("SecurityQuestionError")
    SECURITY_QUESTION_ERROR("SecurityQuestionError"),
    @SerializedName("TemporaryFailureError")
    TEMPORARY_FAILURE_ERROR("TemporaryFailureError");

    operator fun invoke(type : String) = values().firstOrNull { type == it.type }
}