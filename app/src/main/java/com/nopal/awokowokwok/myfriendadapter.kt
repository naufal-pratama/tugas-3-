package com.nopal.awokowokwok

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friend_item.*
import kotlinx.android.synthetic.main.my_friend_item.view.*


class myfriendadapter(
    private val context: Context,
    private val item: List<mapren>,
    private val listener: (mapren) -> Unit
) :
    RecyclerView.Adapter<myfriendadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            context, LayoutInflater.from(context).inflate(R.layout.my_friend_item, parent, false)
        )

    override fun onBindViewHolder(holder: myfriendadapter.ViewHolder, position: Int) {
        holder.bindItem(item.get(position), listener)
    }

    override fun getItemCount(): Int = item.size


    class ViewHolder(
        val context: Context,
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: mapren, listener: (mapren) -> Unit) {
            txtFriendName.text = item.nama
            txtFriendEmail.text = item.email
            txtFriendTelp.text = item.telp
            itemView.setOnClickListener { listener(item) }
    }
}}
