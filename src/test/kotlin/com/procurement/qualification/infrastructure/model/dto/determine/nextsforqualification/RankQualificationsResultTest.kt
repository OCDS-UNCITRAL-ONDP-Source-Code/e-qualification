package com.procurement.qualification.infrastructure.model.dto.determine.nextsforqualification

import com.procurement.qualification.infrastructure.handler.determine.nextforqualification.RankQualificationsResult
import com.procurement.qualification.infrastructure.model.dto.AbstractDTOTestBase
import org.junit.jupiter.api.Test

class RankQualificationsResultTest : AbstractDTOTestBase<RankQualificationsResult>(
    RankQualificationsResult::class.java) {

    @Test
    fun full() {
        testBindingAndMapping(pathToJsonFile = "json/dto/rank.qualification/rank_qualifications_result_full.json")
    }
}
