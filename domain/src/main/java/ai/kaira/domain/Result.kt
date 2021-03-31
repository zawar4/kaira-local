package ai.kaira.domain


data class Result<T>(val data: T?,val status: ResultState, val message: String?) {
    companion object {
        fun <T> success(data: T? = null): Result<T> = Result(status = ResultState.SUCCESS, data = data, message = null)

        fun <T> error(data: T? = null, message: String): Result<T> = Result(status = ResultState.ERROR, data = data, message = message)

        fun <T> loading(data: T? = null): Result<T> = Result(status = ResultState.LOADING, data = data, message = null)
    }
}