package com.example.realmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.realmdemo.model.PlayList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        makeButton.setOnClickListener {
            makeData()
        }
    }

    private fun makeData() {
        val random = (Math.random() * 10).toInt() + 1
        var tempMediaIds = ""
        for (i in 0 .. random) {
            if (i != 0)
                tempMediaIds += ","
            tempMediaIds += UUID.randomUUID().toString()
        }
        PlayList.insertOrUpdate(PlayList().apply {
            name = "${System.currentTimeMillis()}"
            mediaIds = tempMediaIds
        })
        initData()
    }

    private fun initRecyclerView() {
        playListsView.customAdapter.apply {
            onClick = {
                Toast.makeText(this@MainActivity, it.mediaIds, Toast.LENGTH_SHORT).show()
            }
            onDelete = {
                showConfirmDeleteDialog(it)
            }
        }
    }

    private fun showConfirmDeleteDialog(data: PlayList) {
        AlertDialog.Builder(this)
            .setTitle(R.string.title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                delete(data)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create()
            .show()
    }

    private fun delete(data: PlayList) {
        PlayList.delete(data.id)
        initData()
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        playListsView.customAdapter.refresh(PlayList.findAll())
        swipeRefreshLayout.isRefreshing = false
    }
}
