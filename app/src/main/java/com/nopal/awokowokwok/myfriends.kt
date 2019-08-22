package com.nopal.awokowokwok

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nopal.awokowokwok.data.AppDatabase
import com.nopal.awokowokwok.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.main_frame_fragment.*
import kotlinx.android.synthetic.main.my_friend_add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.FieldPosition

class myfriends: Fragment(){
    companion object{
        fun newInstance() : myfriends {
            return myfriends()
        }
    }

    lateinit var listTeman : MutableList<mapren>

    private var db: AppDatabase? = null
    private var MyFriendDao: MyFriendDao? = null
    lateinit var adapter: myfriendadapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_frame_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener{
            (activity as MainActivity).tampilAddFriendFragment()
        }
      //  simulasidatateman()
        listTeman = ArrayList()
        initLocalDB()
        ambildatateman()
        initadapter()
    }

        private fun initLocalDB() {
            db = AppDatabase.getAppDataBase(activity!!)
            MyFriendDao = db?.MyFriendDao()
        }

    private fun tampilTeman(){
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        listMyFriends.adapter = myfriendadapter(activity!!,listTeman!!){
            val friend = it
        }
    }

    private fun initadapter(){
        adapter = myfriendadapter(activity!!, listTeman){

        }
    }

    private fun ambildatateman(){
        listTeman = ArrayList()
        MyFriendDao?.ambilSemuaTeman()?.observe(this, Observer { r ->
            listTeman = r.toMutableList()
        when{
            listTeman.size == 0 -> tampilToast("belum ada data teman")
            else ->{
                tampilTeman()
            }
        }
        })
    }

    private fun tampilToast(message: String){
        Toast.makeText(activity!!,message, Toast.LENGTH_SHORT).show()
    }

  /*  private fun simulasidatateman(){
        listTeman = ArrayList()

        listTeman.add(mapren("udin","laki-laki","udin@yahoo.com","081924243","cileungsi"))
        listTeman.add(mapren("asep","laki-laki","asep@yahoo.com","087684567","cibubur"))
    }*/

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun confirmdialog(friend: mapren, position: Int){
        AlertDialog.Builder(activity!!)
            .setTitle("Delete ${friend.nama}")
            .setMessage("Do you really want to delete ${friend.nama} ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener{dialog, whichButton ->
                    deletefriend(friend)
                    adapter.notifyItemRemoved(position)
                }).show()
    }

    private fun deletefriend(friend: mapren): Job{
        return GlobalScope.launch { MyFriendDao?.deletefriend(friend) }
    }
}