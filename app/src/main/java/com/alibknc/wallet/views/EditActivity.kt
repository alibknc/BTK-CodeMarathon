package com.alibknc.wallet.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.alibknc.wallet.R
import com.alibknc.wallet.models.Card
import com.alibknc.wallet.viewmodels.EditViewModel
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult
import com.huawei.hms.mlsdk.common.MLApplication

class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel

    private lateinit var scanButton: Button
    private lateinit var saveButton: Button
    private lateinit var cardNumber: EditText
    private lateinit var cardDate: EditText
    private lateinit var cvv: EditText
    private lateinit var cardNumberText: TextView
    private lateinit var cardDateText: TextView
    private lateinit var cvvText: TextView
    private var card = Card()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        MLApplication.getInstance().apiKey =
            "DAEDAJKvjBIgsqSB0AAVvkJDt+2wg/NS450YID0jiwu/RS4BjYQpsdDomWKNto+NXO6S6+Bql8IqVkG0kiQWEjt2QFPaDo419mg5VA=="
        intent.getStringExtra("id")?.let {
            card = viewModel.getCard(it)
        }
        createView(card.id != null)
    }

    private fun createView(isUpdate: Boolean) {
        scanButton = findViewById(R.id.scanButton)
        saveButton = findViewById(R.id.saveButton)
        cardNumber = findViewById(R.id.cardNumber)
        cardDate = findViewById(R.id.cardDate)
        cvv = findViewById(R.id.CVV)
        cardNumberText = findViewById(R.id.cardNumberText)
        cardDateText = findViewById(R.id.cardDateText)
        cvvText = findViewById(R.id.cvvText)

        if (isUpdate) {
            saveButton.text = "Güncelle"
            cardNumber.setText(card.cardNumber)
            cardDate.setText(card.cardExpire)
            cvv.setText(card.cvv)
            cardNumberText.text = card.cardNumber
            cardDateText.text = card.cardExpire
            cvvText.text = card.cvv
        }

        scanButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            } else {
                startCaptureActivity(callback)
            }
        }

        saveButton.setOnClickListener {
            val previousCN = card.cardNumber
            card.cardNumber = cardNumber.text.toString()
            card.cardExpire = cardDate.text.toString()
            card.cvv = cvv.text.toString()

            if (card.cardNumber!!.length == 16 && card.cardExpire!!.length == 5 && card.cvv!!.length == 3) {
                val result = if (isUpdate) {
                    viewModel.updateCard(card, previousCN!!)
                } else {
                    viewModel.saveCard(card)
                }
                if (result == -2 || result == (-2).toLong()) {
                    Toast.makeText(
                        applicationContext,
                        "Kart Numarası Zaten Mevcut!",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (result != (-1).toLong()) {
                    val intent = Intent()
                    setResult(2, intent)
                    finish()
                }
            } else {
                Toast.makeText(applicationContext, "Eksik Alanları Doldurunuz!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        cardNumber.addTextChangedListener {
            cardNumberText.text = it
        }

        cardDate.addTextChangedListener {
            if (it!!.isNotEmpty() && it.length == 1) {
                val c: Char = it[it.length - 1]
                if ('/' == c) {
                    it.delete(it.length - 1, it.length)
                }
            }
            if (it.isNotEmpty() && it.length % 2 == 0) {
                val c: Char = it[it.length - 1]
                if ('/' == c) {
                    it.insert(0, "0")
                }
            }
            if (it.isNotEmpty() && it.length % 3 == 0) {
                val c: Char = it[it.length - 1]
                if ('/' == c) {
                    it.delete(it.length - 1, it.length)
                }
            }
            if (it.isNotEmpty() && it.length % 3 == 0) {
                val c: Char = it[it.length - 1]
                if (Character.isDigit(c) && TextUtils.split(it.toString(), "/").size <= 2) {
                    it.insert(it.length - 1, "/")
                }
            }

            cardDateText.text = it
        }

        cvv.addTextChangedListener {
            card.cvv = it.toString()
            cvvText.text = it
        }
    }

    private fun startCaptureActivity(callback: MLBcrCapture.Callback) {
        val config = MLBcrCaptureConfig.Factory()
            .setResultType(MLBcrCaptureConfig.RESULT_ALL)
            .setOrientation(MLBcrCaptureConfig.ORIENTATION_AUTO)
            .create()
        val bankCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config)
        bankCapture.captureFrame(this, callback)
    }

    private val callback: MLBcrCapture.Callback = object : MLBcrCapture.Callback {
        override fun onSuccess(bankCardResult: MLBcrCaptureResult) {
            card = Card()
            card.cardNumber = bankCardResult.number
            card.cardExpire = bankCardResult.expire
            card.cardIssuer = bankCardResult.issuer
            card.cardOrg = bankCardResult.organization
            card.cardType = bankCardResult.type

            cardNumber.setText(card.cardNumber)
            cardDate.setText(card.cardExpire)
            cardNumberText.text = card.cardNumber
            cardDateText.text = card.cardExpire
        }

        override fun onCanceled() {

        }

        override fun onFailure(retCode: Int, bitmap: Bitmap) {

        }

        override fun onDenied() {

        }
    }
}