package com.nopal.awokowokwok

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_frame_fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_friend_add_fragment)
        setSupportActionBar(toolbar)

    }

    private fun gantiFragment(fragmentManager: FragmentManager, fragment:Fragment, frameId:Int){
        val transaction =fragmentManager.beginTransaction()
        transaction.replace(frameId,fragment)
        transaction.commit()
    }
    fun tampilMyFriendFragment(){
        gantiFragment(supportFragmentManager,myfriends.newInstance(),R.id.framelayout)
    }
    fun tampilAddFriendFragment(){
        gantiFragment(supportFragmentManager,addfriend.newInstance(),R.id.framelayout)
    }

}
