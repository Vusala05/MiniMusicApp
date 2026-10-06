package com.example.core_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse <T>(
    @SerialName("headers")
    val headers : HeaderResponse,
    @SerialName("results")
    val results : T?
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
    fun toUiModel(): ErrorModelDo {
        return ErrorModelDo(
            code = this.code,
            errorMessage = this.errorMessage,
            warnings = this.warnings
        )
    }
}

