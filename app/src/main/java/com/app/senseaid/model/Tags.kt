package com.app.senseaid.model

enum class Tags {
    LOUD_SOUNDS,
    QUIET_SOUNDS,
    SOFT_LIGHTS,
    HARSH_LIGHTS,
    LOW_LIGHTS,
    BRIGHT_LIGHTS,
    ASD_FRIENDLY,
    FEW_PEOPLE,
    MANY_PEOPLE;

    override fun toString(): String {
        return name.lowercase().replace('_', ' ')
    }
}