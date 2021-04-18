package ai.kaira.domain


data class KairaResult<T>(val data: T?,val status: ResultState, val kairaAction: KairaAction?, val message: String?) {
    companion object {
        fun <T> success(data: T? = null): KairaResult<T> = KairaResult(status = ResultState.SUCCESS, data = data, message = null,kairaAction = null)

        fun <T> error(data: T? = null, message: String, kairaAction: KairaAction? = null): KairaResult<T> = KairaResult(status = ResultState.ERROR, data = data, message = message,kairaAction = kairaAction)

        fun <T> exception(data: T? = null, message: String, kairaAction: KairaAction? = null): KairaResult<T> = KairaResult(status = ResultState.EXCEPTION, data = data, message = message,kairaAction = kairaAction)

        fun <T> loading(data: T? = null): KairaResult<T> = KairaResult(status = ResultState.LOADING, data = data, message = null,kairaAction = null)
    }
}