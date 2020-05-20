package com.project.son.app.domain

internal data class QRCodeDomainModel(
    val name: String,
    val artist: String,
    val mbId: String? = null
) {
}
