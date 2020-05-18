package com.igorwojda.showcase.app.presentation.tabs.qrcode

internal data class QRCodeDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
