package com.app.senseaid.model

enum class LocationTags {
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
