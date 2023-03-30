package com.app.senseaid.model

enum class SensoryTags(val isPositive: Boolean) {
    LOUD_SOUNDS(false),
    QUIET_SOUNDS(true),
    SOFT_LIGHTS(true),
    HARSH_LIGHTS(false),
    LOW_LIGHTS(true),
    BRIGHT_LIGHTS(false),
    ASD_FRIENDLY(true),
    FEW_PEOPLE(true),
    MANY_PEOPLE(false);

    override fun toString(): String {
        return name.lowercase().split('_')
            .joinToString(" ") { word ->
                if (word == "asd") {
                    word.uppercase()
                } else {
                    word.replaceFirstChar { it.uppercaseChar() }
                }
            }
    }
}

enum class CategoryTags {
    DEFAULT,
    FOOD_SHOP,
    CAFE,
    CLOTHING_SHOP,
    MARKET,
    RESTAURANT;

    override fun toString(): String {
        return name.lowercase().split('_')
            .joinToString(separator = " ") { word -> word.replaceFirstChar { it.uppercaseChar() } }
    }
}
