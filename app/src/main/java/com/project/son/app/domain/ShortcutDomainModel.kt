package com.project.son.app.domain

internal data class ShortcutDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
