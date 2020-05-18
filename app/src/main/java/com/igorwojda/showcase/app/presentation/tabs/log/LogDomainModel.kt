package com.igorwojda.showcase.app.presentation.tabs.log

internal data class LogDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
