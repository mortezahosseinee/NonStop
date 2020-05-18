package com.igorwojda.showcase.app.presentation.tabs.connection

internal data class ConnectionDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
