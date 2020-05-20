package com.project.son.app.domain

internal data class LogDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
