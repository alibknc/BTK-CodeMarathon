package com.alibknc.wallet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibknc.wallet.viewmodels.HomeViewModel
import com.alibknc.wallet.R
import com.alibknc.wallet.adapters.CardListAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CardListAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val list = viewModel.getCardList()

        layoutManager = LinearLayoutManager(this)
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = layoutManager

        adapter = CardListAdapter(list)
        rv.adapter = adapter

        val ib = findViewById<ImageButton>(R.id.imageButton)
        ib.setOnClickListener {
            val intent = Intent(baseContext, EditActivity::class.java)
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2 || requestCode == 3){
            val list = viewModel.getCardList()
            adapter = CardListAdapter(list)
            val rv = findViewById<RecyclerView>(R.id.recyclerView)
            rv.adapter = adapter
        }
    }
}