package com.procurement.qualification.infrastructure.service

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.qualification.application.service.Logger
import com.procurement.qualification.infrastructure.fail.error.BadRequest
import com.procurement.qualification.infrastructure.web.dto.response.ApiResponse
import com.procurement.qualification.infrastructure.web.parser.tryGetCommand
import com.procurement.qualification.infrastructure.web.response.generator.ApiResponseGenerator.generateResponseOnFailure
import org.springframework.stereotype.Service

@Service
class CommandService(
    private val logger: Logger
) {

    fun execute(node: JsonNode): ApiResponse {
        val command = node.tryGetCommand()
            .doReturn {
                return generateResponseOnFailure(
                    fail = BadRequest(), logger = logger
                )
            }
        return TODO()
    }
}
