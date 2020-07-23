package com.procurement.qualification.infrastructure.handler.check.qualification.protocol

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.qualification.application.service.Logger
import com.procurement.qualification.application.service.QualificationService
import com.procurement.qualification.application.service.Transform
import com.procurement.qualification.domain.functional.ValidationResult
import com.procurement.qualification.infrastructure.fail.Fail
import com.procurement.qualification.infrastructure.handler.validation.AbstractValidationHandler2
import com.procurement.qualification.infrastructure.web.enums.Command2Type
import com.procurement.qualification.infrastructure.web.parser.tryGetParams
import org.springframework.stereotype.Component

@Component
class CheckQualificationsForProtocolHandler(
    val transform: Transform,
    logger: Logger,
    val qualificationService: QualificationService
) : AbstractValidationHandler2<Command2Type, Fail>(logger = logger) {

    override fun execute(node: JsonNode): ValidationResult<Fail> {

        val params = node.tryGetParams(target = CheckQualificationsForProtocolRequest::class.java, transform = transform)
            .doReturn { fail -> return ValidationResult.error(fail) }
            .convert()
            .doReturn { fail -> return ValidationResult.error(fail) }

        return qualificationService.checkQualificationsForProtocol(params = params)
    }

    override val action: Command2Type = Command2Type.CHECK_QUALIFICATIONS_FOR_PROTOCOL
}
