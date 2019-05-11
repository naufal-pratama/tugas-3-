package com.nopal.awokowokwok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friend_add_fragment.*

class addfriend: Fragment(){
    companion object{
        fun newInstance() : addfriend {
            return addfriend()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friend_add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener{
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}