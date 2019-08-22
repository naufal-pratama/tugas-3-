package com.nopal.awokowokwok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        tampilMyFriendFragment()

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
