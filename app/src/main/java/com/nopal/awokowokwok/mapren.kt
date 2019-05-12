package com.nopal.awokowokwok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friend_add_fragment.*

data class  mapren(

    val nama : String,
    val kelamin : String,
    val email : String,
    val telp : String,
    val alamat : String
)