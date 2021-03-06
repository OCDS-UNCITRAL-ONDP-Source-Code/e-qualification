package com.procurement.qualification.infrastructure.handler.set

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.procurement.qualification.infrastructure.model.entity.PeriodEntity
import java.time.LocalDateTime

data class SetQualificationPeriodEndResult(
    @param:JsonProperty("preQualification") @field:JsonProperty("preQualification") val preQualification: PreQualification
) {
    data class PreQualification(
        @param:JsonProperty("period") @field:JsonProperty("period") val period: Period
    ) {
        data class Period(
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @param:JsonProperty("endDate") @field:JsonProperty("endDate") val endDate: LocalDateTime?
        )
    }

    companion object {
        fun fromDomain(periodEntity: PeriodEntity): SetQualificationPeriodEndResult =
            SetQualificationPeriodEndResult(
                preQualification = PreQualification(
                    period = PreQualification.Period(
                        endDate = periodEntity.endDate
                    )
                )
            )
    }
}

