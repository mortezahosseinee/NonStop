package com.project.son.app.view.tabs.qrcode.helper

import android.content.Context
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import timber.log.Timber

class QRCodeHelper() {

    private var mErrorCorrectionLevel: ErrorCorrectionLevel? = null
    private var mMargin = 0
    private var mContent: String? = null
    private var mWidth = 0
    private var mHeight: Int = 0

    /**
     * private constructor of this class only access by stying in this class.
     */
    constructor(context: Context) : this() {
        mHeight = (context.resources.displayMetrics.heightPixels / 2.4).toInt()
        mWidth = (context.resources.displayMetrics.widthPixels / 1.3).toInt()

        Timber.e("Dimension = %s", mHeight.toString())
        Timber.e("Dimension = %s", mWidth.toString())
    }

    /**
     * This method is called generate function who generate the qrcode and return it.
     *
     * @return qrcode image with encrypted user in it.
     */
    fun getQRCOde(): Bitmap? {
        return generate()
    }

    /**
     * Simply setting the correctionLevel to qrcode.
     *
     * @param level ErrorCorrectionLevel for Qrcode.
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    fun setErrorCorrectionLevel(level: ErrorCorrectionLevel?): QRCodeHelper? {
        mErrorCorrectionLevel = level
        return this
    }

    /**
     * Simply setting the encrypted to qrcode.
     *
     * @param content encrypted content for to store in qrcode.
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    fun setContent(content: String?): QRCodeHelper? {
        mContent = content
        return this
    }

    /**
     * Simply setting the width and height for qrcode.
     *
     * @param width  for qrcode it needs to greater than 1.
     * @param height for qrcode it needs to greater than 1.
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    fun setWidthAndHeight(
        width: Int,
        height: Int
    ): QRCodeHelper? {
        mWidth = width
        mHeight = height
        return this
    }

    /**
     * Simply setting the margin for qrcode.
     *
     * @param margin for qrcode spaces. greater than 0
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    fun setMargin(margin: Int): QRCodeHelper? {
        mMargin = margin
        return this
    }

    /**
     * Generate the qrcode with giving the properties.
     *
     * @return the qrcode image.
     */
    private fun generate(): Bitmap? {
        val hintsMap: MutableMap<EncodeHintType, Any?> = HashMap()
        hintsMap[EncodeHintType.CHARACTER_SET] = "utf-8"
        hintsMap[EncodeHintType.ERROR_CORRECTION] = mErrorCorrectionLevel
        hintsMap[EncodeHintType.MARGIN] = mMargin
        try {
            val bitMatrix =
                QRCodeWriter().encode(mContent, BarcodeFormat.QR_CODE, mWidth, mHeight, hintsMap)
            val pixels = IntArray(mWidth * mHeight)
            for (i in 0 until mHeight) {
                for (j in 0 until mWidth) {
                    if (bitMatrix[j, i]) {
                        pixels[i * mWidth + j] = -0x1000000
                    } else {
                        pixels[i * mWidth + j] = 0x000000
                    }
                }
            }
            return Bitmap.createBitmap(pixels, mWidth, mHeight, Bitmap.Config.ARGB_8888)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}
