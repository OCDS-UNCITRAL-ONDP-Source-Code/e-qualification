package com.procurement.qualification.infrastructure.model.dto.determine.nextsforqualification

import com.procurement.qualification.infrastructure.handler.determine.nextforqualification.DetermineNextsForQualificationRequest
import com.procurement.qualification.infrastructure.model.dto.AbstractDTOTestBase
import org.junit.jupiter.api.Test

class DetermineNextsForQualificationRequestTest : AbstractDTOTestBase<DetermineNextsForQualificationRequest>(
    DetermineNextsForQualificationRequest::class.java) {

    @Test
    fun full() {
        testBindingAndMapping(pathToJsonFile = "json/dto/determine.nextsforqualification/determine_nexts_for_qualification_request_full.json")
    }

}
