package com.example.realmdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdemo.R
import com.example.realmdemo.model.PlayList

class PlayListsView: RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val customAdapter by lazy { Adapter(context) }

    init {
        adapter = customAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
    }

    class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        var onClick: ((PlayList) -> Unit)? = null
        var onDelete: ((PlayList) -> Unit)? = null

        private val items = mutableListOf<PlayList>()

        fun refresh(list: List<PlayList>) {
            items.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.play_list_cell, parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is ItemViewHolder)
                onBindViewHolder(holder, position)
        }

        private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val data = items[position]
            holder.apply {
                textView.text = data.id
                deleteButton.setOnClickListener {
                    onDelete?.invoke(data)
                }
                rootView.setOnClickListener {
                    onClick?.invoke(data)
                }
            }
        }

        class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val rootView = view.findViewById<View>(R.id.rootView)
            val textView = view.findViewById<TextView>(R.id.textView)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        }
    }
}