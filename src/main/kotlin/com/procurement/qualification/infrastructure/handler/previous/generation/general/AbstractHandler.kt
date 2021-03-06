package com.procurement.qualification.infrastructure.handler.previous.generation.general

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.qualification.application.service.Logger
import com.procurement.qualification.application.service.Transform
import com.procurement.qualification.domain.functional.Result
import com.procurement.qualification.infrastructure.fail.Fail
import com.procurement.qualification.infrastructure.fail.error.BadRequest
import com.procurement.qualification.infrastructure.handler.Handler
import com.procurement.qualification.infrastructure.web.dto.command.CommandMessage
import com.procurement.qualification.infrastructure.web.dto.command.CommandType
import com.procurement.qualification.infrastructure.web.dto.response.ApiResponse
import com.procurement.qualification.infrastructure.web.dto.response.ApiSuccessResponse
import com.procurement.qualification.infrastructure.web.response.generator.ApiResponseGenerator.generateResponseOnFailure

abstract class AbstractHandler<ACTION : CommandType, R : Any>(
    private val transform: Transform,
    private val logger: Logger
) : Handler<ACTION, ApiResponse> {

    override fun handle(node: JsonNode): ApiResponse {
        val cm = transform.tryMapping(node, CommandMessage::class.java)
            .doReturn { return generateResponseOnFailure(fail = BadRequest(), logger = logger) }

        return when (val result = execute(cm)) {
            is Result.Success -> {
                if (logger.isDebugEnabled)
                    logger.debug(
                        "${action.key} has been executed. Result: '${transform.trySerialization(result.get)}'"
                    )
                return ApiSuccessResponse(version = cm.version, id = cm.id, data = result.get)
            }
            is Result.Failure -> generateResponseOnFailure(
                fail = result.error, version = cm.version, id = cm.id, logger = logger
            )
        }
    }

    abstract fun execute(cm: CommandMessage): Result<R, Fail>
}