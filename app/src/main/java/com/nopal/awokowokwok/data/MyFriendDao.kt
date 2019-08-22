package com.nopal.awokowokwok.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nopal.awokowokwok.mapren

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: mapren)

    @Query("SELECT * FROM mapren")
    fun ambilSemuaTeman(): LiveData<List<mapren>>

    @Delete
    fun deletefriend(friend: mapren)

}