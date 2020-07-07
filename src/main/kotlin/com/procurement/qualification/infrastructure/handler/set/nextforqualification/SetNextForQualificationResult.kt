package com.procurement.qualification.infrastructure.handler.set.nextforqualification

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.procurement.qualification.domain.enums.DocumentType
import com.procurement.qualification.domain.enums.QualificationStatus
import com.procurement.qualification.domain.enums.QualificationStatusDetails
import com.procurement.qualification.domain.model.document.DocumentId
import com.procurement.qualification.domain.model.measure.Scoring
import com.procurement.qualification.domain.model.organization.OrganizationId
import com.procurement.qualification.domain.model.person.PersonId
import com.procurement.qualification.domain.model.qualification.QualificationId
import com.procurement.qualification.domain.model.requirement.RequirementId
import com.procurement.qualification.domain.model.requirement.RequirementResponseValue
import com.procurement.qualification.domain.model.requirementresponse.RequirementResponseId
import com.procurement.qualification.domain.model.submission.SubmissionId
import java.time.LocalDateTime

data class SetNextForQualificationResult(
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @field:JsonProperty("qualifications") @param:JsonProperty("qualifications") val qualifications: List<Qualification>
) {

    data class Qualification(
        @field:JsonProperty("id") @param:JsonProperty("id") val id: QualificationId,
        @field:JsonProperty("date") @param:JsonProperty("date") val date: LocalDateTime,
        @field:JsonProperty("status") @param:JsonProperty("status") val status: QualificationStatus,
        @field:JsonProperty("statusDetails") @param:JsonProperty("statusDetails") val statusDetails: QualificationStatusDetails,
        @field:JsonProperty("relatedSubmission") @param:JsonProperty("relatedSubmission") val relatedSubmission: SubmissionId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @field:JsonProperty("internalId") @param:JsonProperty("internalId") val internalId: String?,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @field:JsonProperty("requirementResponses") @param:JsonProperty("requirementResponses") val requirementResponses: List<RequirementResponse>?,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @field:JsonProperty("scoring") @param:JsonProperty("scoring") val scoring: Scoring?,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @field:JsonProperty("documents") @param:JsonProperty("documents") val documents: List<Document>?
    ) {
        data class RequirementResponse(
            @field:JsonProperty("id") @param:JsonProperty("id") val id: RequirementResponseId,
            @field:JsonProperty("value") @param:JsonProperty("value") val value: RequirementResponseValue,
            @field:JsonProperty("relatedTenderer") @param:JsonProperty("relatedTenderer") val relatedTenderer: RelatedTenderer,
            @field:JsonProperty("requirement") @param:JsonProperty("requirement") val requirement: Requirement,
            @field:JsonProperty("responder") @param:JsonProperty("responder") val responder: Responder
        ) {
            data class RelatedTenderer(
                @field:JsonProperty("id") @param:JsonProperty("id") val id: OrganizationId
            )

            data class Requirement(
                @field:JsonProperty("id") @param:JsonProperty("id") val id: RequirementId
            )

            data class Responder(
                @field:JsonProperty("id") @param:JsonProperty("id") val id: PersonId,
                @field:JsonProperty("name") @param:JsonProperty("name") val name: String
            )
        }

        data class Document(
            @param:JsonProperty("id") @field:JsonProperty("id") val id: DocumentId,
            @param:JsonProperty("documentType") @field:JsonProperty("documentType") val documentType: DocumentType,
            @param:JsonProperty("title") @field:JsonProperty("title") val title: String,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @param:JsonProperty("description") @field:JsonProperty("description") val description: String?
        )
    }
}