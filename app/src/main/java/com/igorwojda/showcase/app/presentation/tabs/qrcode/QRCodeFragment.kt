package com.igorwojda.showcase.app.presentation.tabs.qrcode

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.igorwojda.showcase.R
import com.igorwojda.showcase.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_qr_code.*
import org.kodein.di.generic.instance

class QRCodeFragment : BaseContainerFragment() {

    private val viewModel: QRCodeViewModel by instance()

    override val layoutResourceId = R.layout.fragment_qr_code

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTextFont()
        initNumberPicker()
        createQRCodeCommand(0)
    }

    private fun setTextFont() {
        Typeface.createFromAsset(
            context?.assets,
            "iransans_fa.ttf"
        ).let {
            txv_choose_floor.typeface = it
        }
    }

    private fun initNumberPicker() {
        val minValue = -10
        val maxValue = 50

        npk_floor.let {
            it.minValue = 0
            it.maxValue = maxValue - minValue
            it.value = 0 - minValue
            it.setFormatter { value -> (value + minValue).toString() }
            it.setOnValueChangedListener { picker, oldVal, newVal -> createQRCodeCommand(newVal) }
        }
    }

    private fun createQRCodeCommand(floor: Int) {
        val bitmapQrCode = QRCodeHelper(requireContext())
            .setContent(npk_floor.value.toString())
            ?.setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
            ?.setMargin(2)
            ?.getQRCOde()

        img_command.setImageBitmap(bitmapQrCode)
        img_command.setOnClickListener {
            showQrCodeDialog()
        }
    }

    private fun showQrCodeDialog() {
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_qr_code, null)

        val txvQrCodeScan: TextView = dialogView.findViewById(R.id.txv_qr_code_scan)
        val imgQrCode: ImageView = dialogView.findViewById(R.id.img_qr_code)
        val btnQrCodeScanDone: Button = dialogView.findViewById(R.id.btn_qr_code_scan_done)

        Typeface.createFromAsset(
            context?.assets,
            "iransans_fa.ttf"
        ).let {
            txvQrCodeScan.typeface = it
            btnQrCodeScanDone.typeface = it
        }

        imgQrCode.setImageDrawable(img_command.drawable)

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener { }
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        btnQrCodeScanDone.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
