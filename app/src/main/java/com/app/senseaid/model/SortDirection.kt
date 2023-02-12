package com.app.senseaid.model

enum class SortDirection {
    ASCENDING,
    DESCENDING,
    HIGHEST_RATING,
    LOWEST_RATING;

    override fun toString(): String {
        return name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercaseChar() }
    }
}
