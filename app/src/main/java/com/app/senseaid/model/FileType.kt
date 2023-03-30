package com.app.senseaid.model

enum class FileType {
    AUDIO,
    LOCATIONS;

    override fun toString(): String {
        return name.lowercase()
    }
}