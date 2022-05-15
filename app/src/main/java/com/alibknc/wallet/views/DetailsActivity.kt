package com.alibknc.wallet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.alibknc.wallet.R
import com.alibknc.wallet.models.Cards
import com.alibknc.wallet.viewmodels.DetailsViewModel

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailsViewModel

    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button
    private lateinit var d_cardNumberText: TextView
    private lateinit var d_cardDateText: TextView
    private lateinit var d_cvvText: TextView
    private lateinit var d_idText: TextView
    private lateinit var card: Cards

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        card = intent.getStringExtra("id")?.let { viewModel.getCard(it) }!!
        createView()
    }

    private fun createView(){
        deleteButton = findViewById(R.id.deleteButton)
        updateButton = findViewById(R.id.updateButton)
        d_cardNumberText = findViewById(R.id.d_cardNumberText)
        d_cardDateText = findViewById(R.id.d_cardDateText)
        d_cvvText = findViewById(R.id.d_cvvText)
        d_idText = findViewById(R.id.d_idText)

        d_cardNumberText.text = card.cardNumber
        d_cardDateText.text = card.cardExpire
        d_cvvText.text = card.cvv
        d_idText.text = card.id

        deleteButton.setOnClickListener {
            val result = viewModel.deleteCard(card.id!!)
            if(result != -1){
                val intent = Intent()
                setResult(3, intent)
                finish()
            }
        }

        updateButton.setOnClickListener {
            val intent = Intent(baseContext, EditActivity::class.java)
            intent.putExtra("id", card.id)
            startActivityForResult(intent, 2)
            finish()
        }
    }

}