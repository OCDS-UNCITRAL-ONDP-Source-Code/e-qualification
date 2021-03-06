package com.procurement.qualification.infrastructure.handler.historical

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.qualification.application.repository.HistoryRepository
import com.procurement.qualification.application.service.Logger
import com.procurement.qualification.application.service.Transform
import com.procurement.qualification.domain.functional.Result
import com.procurement.qualification.infrastructure.fail.Fail
import com.procurement.qualification.infrastructure.handler.Handler
import com.procurement.qualification.infrastructure.repository.HistoryRepositoryCassandra
import com.procurement.qualification.infrastructure.web.dto.Action
import com.procurement.qualification.infrastructure.web.dto.response.ApiResponse2
import com.procurement.qualification.infrastructure.web.dto.response.ApiSuccessResponse2
import com.procurement.qualification.infrastructure.web.parser.tryGetId
import com.procurement.qualification.infrastructure.web.parser.tryGetVersion
import com.procurement.qualification.infrastructure.web.response.generator.ApiResponse2Generator.generateResponseOnFailure

abstract class AbstractHistoricalHandler2<ACTION : Action, R : Any>(
    private val target: Class<R>,
    private val historyRepository: HistoryRepository,
    val transform: Transform,
    private val logger: Logger
) : Handler<ACTION, ApiResponse2> {

    override fun handle(node: JsonNode): ApiResponse2 {
        val id = node.tryGetId().get
        val version = node.tryGetVersion().get

        val history = historyRepository.getHistory(id.toString(), action.key)
            .doOnError { error ->
                return generateResponseOnFailure(
                    fail = error, version = version, id = id, logger = logger
                )
            }
            .get
        if (history != null) {
            val data = history.jsonData
            val result = transform.tryDeserialization(value = data, target = target)
                .doReturn { incident ->
                    return generateResponseOnFailure(
                        fail = Fail.Incident.Database.Parsing(
                            column = HistoryRepositoryCassandra.JSON_DATA, value = data, exception = incident.exception
                        ),
                        id = id,
                        version = version,
                        logger = logger
                    )
                }
            return ApiSuccessResponse2(version = version, id = id, result = result)
        }

        return when (val result = execute(node)) {
            is Result.Success -> {
                val resultData = result.get
                historyRepository.saveHistory(id.toString(), action.key, resultData)
                if (logger.isDebugEnabled)
                    logger.debug("${action.key} has been executed. Result: '${transform.trySerialization(result.get)}'")

                ApiSuccessResponse2(version = version, id = id, result = resultData)
            }
            is Result.Failure -> generateResponseOnFailure(
                fail = result.error, version = version, id = id, logger = logger
            )
        }
    }

    abstract fun execute(node: JsonNode): Result<R, Fail>
}

