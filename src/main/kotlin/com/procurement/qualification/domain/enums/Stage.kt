package com.procurement.qualification.domain.enums

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Stage(@JsonValue override val key: String) : EnumElementProvider.Key {

    AC("AC"),
    EI("EI"),
    EV("EV"),
    FE("FE"),
    FS("FS"),
    NP("NP"),
    PN("PN"),
    TP("TP");

    override fun toString(): String = key

    companion object : EnumElementProvider<Stage>(info = info()) {

        @JvmStatic
        @JsonCreator
        fun creator(name: String) = orThrow(name)
    }
}
