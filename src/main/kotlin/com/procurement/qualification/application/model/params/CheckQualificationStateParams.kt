package com.procurement.qualification.application.model.params

import com.procurement.qualification.application.model.parseCpid
import com.procurement.qualification.application.model.parseEnum
import com.procurement.qualification.application.model.parseOcid
import com.procurement.qualification.application.model.parseQualificationId
import com.procurement.qualification.domain.enums.OperationType
import com.procurement.qualification.domain.enums.ProcurementMethodDetails
import com.procurement.qualification.domain.functional.Result
import com.procurement.qualification.domain.functional.asSuccess
import com.procurement.qualification.domain.model.Cpid
import com.procurement.qualification.domain.model.Ocid
import com.procurement.qualification.domain.model.qualification.QualificationId
import com.procurement.qualification.infrastructure.fail.error.DataErrors

class CheckQualificationStateParams private constructor(
    val cpid: Cpid,
    val ocid: Ocid,
    val country: String,
    val pmd: ProcurementMethodDetails,
    val operationType: OperationType,
    val qualificationId: QualificationId
) {
    companion object {

        private val allowedPmd = ProcurementMethodDetails.allowedElements
            .filter {
                when (it) {
                    ProcurementMethodDetails.CF, ProcurementMethodDetails.TEST_CF,
                    ProcurementMethodDetails.GPA, ProcurementMethodDetails.TEST_GPA,
                    ProcurementMethodDetails.OF, ProcurementMethodDetails.TEST_OF,
                    ProcurementMethodDetails.RT, ProcurementMethodDetails.TEST_RT -> true

                    ProcurementMethodDetails.CD, ProcurementMethodDetails.TEST_CD,
                    ProcurementMethodDetails.DA, ProcurementMethodDetails.TEST_DA,
                    ProcurementMethodDetails.DC, ProcurementMethodDetails.TEST_DC,
                    ProcurementMethodDetails.FA, ProcurementMethodDetails.TEST_FA,
                    ProcurementMethodDetails.IP, ProcurementMethodDetails.TEST_IP,
                    ProcurementMethodDetails.MV, ProcurementMethodDetails.TEST_MV,
                    ProcurementMethodDetails.NP, ProcurementMethodDetails.TEST_NP,
                    ProcurementMethodDetails.OP, ProcurementMethodDetails.TEST_OP,
                    ProcurementMethodDetails.OT, ProcurementMethodDetails.TEST_OT,
                    ProcurementMethodDetails.SV, ProcurementMethodDetails.TEST_SV -> false
                }
            }
            .toSet()

        private val allowedOperationType = OperationType.allowedElements
            .filter {
                when (it) {
                    OperationType.QUALIFICATION,
                    OperationType.QUALIFICATION_CONSIDERATION,
                    OperationType.QUALIFICATION_DECLARE_NON_CONFLICT_OF_INTEREST -> true
                    OperationType.QUALIFICATION_PROTOCOL -> false
                }
            }
            .toSet()

        fun tryCreate(
            cpid: String,
            ocid: String,
            country: String,
            pmd: String,
            operationType: String,
            qualificationId: String
        ): Result<CheckQualificationStateParams, DataErrors> {

            val parsedCpid = parseCpid(value = cpid)
                .orForwardFail { fail -> return fail }

            val parsedOcid = parseOcid(value = ocid)
                .orForwardFail { fail -> return fail }

            val parsedPmd = parseEnum(value = pmd, allowedEnums = allowedPmd, attributeName = "pmd", target = ProcurementMethodDetails)
                .orForwardFail { fail -> return fail }

            val parsedOperationType = parseEnum(
                value = operationType,
                target = OperationType,
                attributeName = "operationType",
                allowedEnums = allowedOperationType
            )
                .orForwardFail { fail -> return fail }

            val parsedQualificationId = parseQualificationId(value = qualificationId)
                .orForwardFail { fail -> return fail }

            return CheckQualificationStateParams(
                cpid = parsedCpid,
                ocid = parsedOcid,
                pmd = parsedPmd,
                country = country,
                operationType = parsedOperationType,
                qualificationId = parsedQualificationId
            )
                .asSuccess()
        }
    }
}
