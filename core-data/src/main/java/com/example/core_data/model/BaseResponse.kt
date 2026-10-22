package com.example.core_data.model

import com.example.core_domain.model.ErrorModelDo
import com.example.core_domain.model.ErrorType
import com.example.core_domain.model.fromHttpCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse <T>(
    @SerialName("headers")
    val headers : HeaderResponse,
    @SerialName("results")
    val results : T? = null
) {

}



@Serializable
data class HeaderResponse(
    @SerialName("status")
    val status : String?,
    @SerialName("code")
    val code : Int?,
    @SerialName("error_message")
    val errorMessage : String?,
    @SerialName("warnings")
    val warnings : String?,
    @SerialName("results_count")
    val resultsCount : Long?
) {
    fun toErrorModel(): ErrorModelDo {
        return ErrorModelDo(
            errorType = ErrorType.API_ERROR,
            errorMessage = this.errorMessage,
            warnings = this.warnings
        )
    }
}
@Serializable
data class ErrorResponse(
    @SerialName("headers") val headers: HeaderResponse
)

