package com.procurement.qualification.infrastructure.handler.check.declaration

import com.procurement.qualification.application.model.params.CheckDeclarationParams
import com.procurement.qualification.domain.functional.Result
import com.procurement.qualification.infrastructure.fail.error.DataErrors

fun CheckDeclarationRequest.convert(): Result<CheckDeclarationParams, DataErrors> =
    CheckDeclarationParams.tryCreate(
        cpid = this.cpid,
        qualificationId = this.qualificationId,
        ocid = this.ocid,
        requirementResponse = this.requirementResponse
            .convert()
            .orForwardFail { fail -> return fail },
        criteria = this.criteria
            .map {
                it.convert()
                    .orForwardFail { fail -> return fail }
            }
    )

fun CheckDeclarationRequest.RequirementResponse.convert(): Result<CheckDeclarationParams.RequirementResponse, DataErrors> =
    CheckDeclarationParams.RequirementResponse.tryCreate(
        id = this.id,
        value = this.value,
        relatedTendererId = this.relatedTendererId,
        responder = this.responder
            .convert()
            .orForwardFail { fail -> return fail },
        requirementId = this.requirementId
    )

fun CheckDeclarationRequest.RequirementResponse.Responder.convert(): Result<CheckDeclarationParams.RequirementResponse.Responder, DataErrors> =
    CheckDeclarationParams.RequirementResponse.Responder.tryCreate(id = this.id, name = this.name)

fun CheckDeclarationRequest.Criteria.convert(): Result<CheckDeclarationParams.Criteria, DataErrors> =
    CheckDeclarationParams.Criteria.tryCreate(
        id = this.id,
        requirementGroups = this.requirementGroups
            .map {
                it.convert()
                    .orForwardFail { fail -> return fail }
            }
    )

fun CheckDeclarationRequest.Criteria.RequirementGroup.convert(): Result<CheckDeclarationParams.Criteria.RequirementGroup, DataErrors> =
    CheckDeclarationParams.Criteria.RequirementGroup.tryCreate(
        id = this.id,
        requirements = this.requirements
            .map {
                it.convert()
                    .orForwardFail { fail -> return fail }
            }
    )

fun CheckDeclarationRequest.Criteria.RequirementGroup.Requirement.convert(): Result<CheckDeclarationParams.Criteria.RequirementGroup.Requirement, DataErrors> =
    CheckDeclarationParams.Criteria.RequirementGroup.Requirement.tryCreate(id = this.id, dataType = this.dataType)
