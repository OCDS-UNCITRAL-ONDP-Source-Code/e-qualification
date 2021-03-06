package com.procurement.qualification.infrastructure.handler.create.declaration

import com.fasterxml.jackson.annotation.JsonProperty
import com.procurement.qualification.domain.model.requirement.RequirementResponseValue

data class DoDeclarationRequest(
    @param:JsonProperty("cpid") @field:JsonProperty("cpid") val cpid: String,
    @param:JsonProperty("ocid") @field:JsonProperty("ocid") val ocid: String,
    @param:JsonProperty("qualifications") @field:JsonProperty("qualifications") val qualifications: List<Qualification>
) {
    data class Qualification(
        @param:JsonProperty("id") @field:JsonProperty("id") val id: String,
        @param:JsonProperty("requirementResponses") @field:JsonProperty("requirementResponses") val requirementResponses: List<RequirementResponse>
    ) {
        data class RequirementResponse(
            @param:JsonProperty("id") @field:JsonProperty("id") val id: String,
            @param:JsonProperty("value") @field:JsonProperty("value") val value: RequirementResponseValue,
            @param:JsonProperty("relatedTenderer") @field:JsonProperty("relatedTenderer") val relatedTenderer: RelatedTenderer,
            @param:JsonProperty("requirement") @field:JsonProperty("requirement") val requirement: Requirement,
            @param:JsonProperty("responder") @field:JsonProperty("responder") val responder: Responder
        ) {
            data class RelatedTenderer(
                @param:JsonProperty("id") @field:JsonProperty("id") val id: String
            )

            data class Requirement(
                @param:JsonProperty("id") @field:JsonProperty("id") val id: String
            )

            data class Responder(
                @param:JsonProperty("id") @field:JsonProperty("id") val id: String,
                @param:JsonProperty("name") @field:JsonProperty("name") val name: String
            )
        }
    }
}
