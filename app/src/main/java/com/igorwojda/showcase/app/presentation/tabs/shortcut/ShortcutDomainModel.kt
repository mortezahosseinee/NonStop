package com.igorwojda.showcase.app.presentation.tabs.shortcut

internal data class ShortcutDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
