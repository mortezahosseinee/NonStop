package com.project.son.app.view.tabs.qrcode.presentation

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.project.son.R
import com.project.son.app.helper.classes.VoiceToSpeechHelper.getFloor
import com.project.son.app.view.tabs.qrcode.helper.QRCodeHelper
import com.project.son.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_qr_code.*
import org.kodein.di.generic.instance
import java.util.*

class QRCodeFragment : BaseContainerFragment() {

    private var REQ_CODE_SPEECH_INPUT = 3000

    private val viewModel: QRCodeViewModel by instance()

    override val layoutResourceId = R.layout.fragment_qr_code

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTextFont()
        initNumberPicker()
        createQRCodeCommand(0)
        initVoiceInput()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    val floorPair = getFloor(result)
                    when (floorPair.second) {
                        true -> scrollNumberPicker(floorPair.first.toInt())
                        false -> showSnackbar(floorPair.first, false)
                    }
                }
            }
        }
    }

    private fun scrollNumberPicker(floor: Int) {
        npk_floor.value = floor
        createQRCodeCommand(npk_floor.value)
    }

    private fun initVoiceInput() {
        img_record.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa_IR")
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale("fa_IR"))
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, Locale("fa_IR"))
            intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "speech_prompt"
            )
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
            } catch (a: ActivityNotFoundException) {
                showSnackbar("دریافت اطلاعات صوتی در دستگاه شما، پشتیبانی نمی شود.", false)
            }
        }
    }

    private fun setTextFont() {
        Typeface.createFromAsset(
            context?.assets,
            "iransans_fa.ttf"
        ).let {
            txv_choose_floor.typeface = it
            npk_floor.setSelectedTypeface(it)
        }
    }

    private fun initNumberPicker() {
        npk_floor.setOnValueChangedListener { picker, oldVal, newVal -> createQRCodeCommand(newVal) }
    }

    private fun createQRCodeCommand(floor: Int) {
        val bitmapQrCode = QRCodeHelper(requireContext())
            .setContent(floor.toString())
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
