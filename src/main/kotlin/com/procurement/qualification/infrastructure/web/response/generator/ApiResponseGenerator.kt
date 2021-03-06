package com.procurement.qualification.infrastructure.web.response.generator

import com.procurement.qualification.application.service.Logger
import com.procurement.qualification.infrastructure.configuration.properties.GlobalProperties
import com.procurement.qualification.infrastructure.fail.Fail
import com.procurement.qualification.infrastructure.web.dto.command.ApiVersion
import com.procurement.qualification.infrastructure.web.dto.response.ApiErrorResponse

object ApiResponseGenerator {

    fun generateResponseOnFailure(
        fail: Fail, version: ApiVersion = GlobalProperties.App.apiVersion, logger: Logger, id: String = "N/A"
    ): ApiErrorResponse {
        fail.logging(logger)
        return ApiErrorResponse(
            errors = listOf(
                ApiErrorResponse.Error(
                    code = "${fail.code}/${GlobalProperties.serviceId}",
                    description = fail.description
                )
            ),
            id = id,
            version = version
        )
    }
}