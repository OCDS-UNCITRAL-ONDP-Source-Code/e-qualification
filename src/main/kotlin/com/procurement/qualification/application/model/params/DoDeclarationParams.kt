package com.procurement.qualification.application.model.params

import com.procurement.qualification.application.model.parseCpid
import com.procurement.qualification.application.model.parseOcid
import com.procurement.qualification.application.model.parseQualificationId
import com.procurement.qualification.domain.functional.Result
import com.procurement.qualification.domain.functional.asFailure
import com.procurement.qualification.domain.functional.asSuccess
import com.procurement.qualification.domain.model.Cpid
import com.procurement.qualification.domain.model.Ocid
import com.procurement.qualification.domain.model.organization.OrganizationId
import com.procurement.qualification.domain.model.person.PersonId
import com.procurement.qualification.domain.model.qualification.QualificationId
import com.procurement.qualification.domain.model.requirement.RequirementId
import com.procurement.qualification.domain.model.requirement.RequirementResponseValue
import com.procurement.qualification.domain.model.requirementresponse.RequirementResponseId
import com.procurement.qualification.infrastructure.fail.error.DataErrors

class DoDeclarationParams private constructor(
    val cpid: Cpid,
    val ocid: Ocid,
    val qualifications: List<Qualification>
) {

    companion object {
        fun tryCreate(
            cpid: String,
            ocid: String,
            qualifications: List<Qualification>
        ): Result<DoDeclarationParams, DataErrors> {

            val parsedCpid = parseCpid(value = cpid)
                .orForwardFail { fail -> return fail }

            val parsedOcid = parseOcid(value = ocid)
                .orForwardFail { fail -> return fail }

            return DoDeclarationParams(cpid = parsedCpid, ocid = parsedOcid, qualifications = qualifications)
                .asSuccess()
        }
    }

    class Qualification private constructor(
        val id: QualificationId,
        val requirementResponses: List<RequirementResponse>
    ) {

        companion object {
            fun tryCreate(
                id: String,
                requirementResponses: List<RequirementResponse>
            ): Result<Qualification, DataErrors> {
                val parsedId = parseQualificationId(value = id)
                    .orForwardFail { fail -> return fail }
                return Qualification(id = parsedId, requirementResponses = requirementResponses)
                    .asSuccess()
            }
        }

        class RequirementResponse private constructor(
            val id: RequirementResponseId,
            val value: RequirementResponseValue,
            val relatedTenderer: RelatedTenderer,
            val requirement: Requirement,
            val responder: Responder
        ) {
            companion object {
                fun tryCreate(
                    id: String,
                    value: RequirementResponseValue,
                    relatedTenderer: RelatedTenderer,
                    requirement: Requirement,
                    responder: Responder
                ): Result<RequirementResponse, DataErrors> {

                    val parsedId = RequirementResponseId.tryCreate(text = id)
                        .orForwardFail { fail -> return fail }

                    return RequirementResponse(
                        id = parsedId,
                        value = value,
                        relatedTenderer = relatedTenderer,
                        requirement = requirement,
                        responder = responder
                    )
                        .asSuccess()
                }
            }

            class RelatedTenderer private constructor(val id: OrganizationId) {
                companion object {
                    fun tryCreate(id: String): Result<RelatedTenderer, DataErrors> {

                        val parsedOrganizationId = OrganizationId.parse(id)
                            ?: return DataErrors.Validation.EmptyString(name = id)
                                .asFailure()

                        return RelatedTenderer(id = parsedOrganizationId)
                            .asSuccess()
                    }
                }
            }

            class Requirement private constructor(val id: RequirementId) {
                companion object {
                    fun tryCreate(id: String): Result<Requirement, DataErrors> {

                        val parsedRequirementId = RequirementId.parse(id)
                            ?: return DataErrors.Validation.EmptyString(name = id)
                                .asFailure()

                        return Requirement(id = parsedRequirementId)
                            .asSuccess()
                    }
                }
            }

            class Responder private constructor(val id: PersonId, val name: String) {
                companion object {
                    fun tryCreate(id: String, name: String): Result<Responder, DataErrors> {

                        val parsedId = PersonId.parse(id)
                            ?: return DataErrors.Validation.EmptyString(name = id)
                                .asFailure()

                        return Responder(id = parsedId, name = name)
                            .asSuccess()
                    }
                }
            }
        }
    }
}
