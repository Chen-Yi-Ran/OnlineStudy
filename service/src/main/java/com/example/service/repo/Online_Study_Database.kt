package com.example.service.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database
import androidx.room.TypeConverter

//app的公共业务基础数据类
//1.entity的定义
@Entity(tableName = "tb_onlineStudy_user")
data class UserInfo(
    @Embedded
    @PrimaryKey
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
)
@TypeConverters(DataItemConverter::class)
data class Data(
    val admin: Boolean,
    @TypeConverters(DataItemConverter::class)
    val chapterTops: List<Any>,
    val coinCount: Int,
    @TypeConverters(DataItemConverter::class)
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
//2.dao层的定义
@Dao
interface UserDao{

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insertUser(user:UserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserInfo)

    @Delete
    fun deleteUser(user: UserInfo)

    @Query("select * from tb_onlineStudy_user where id=:id")
    fun queryLiveUser(id: Int=0):LiveData<UserInfo>

    @Query("select * from tb_onlineStudy_user where id=:id")
    fun queryUser(id:Int=0):UserInfo
}

//3.database
@Database(entities = [UserInfo::class],version = 1,exportSchema = false)
abstract class Online_Study_Database:RoomDatabase() {
    abstract val userDao:UserDao
    companion object{

        private const val DB_NAME="online_Study_db"

        @Volatile
        private var sInstance:Online_Study_Database?=null

        @Synchronized
        fun getInstance(context: Context):Online_Study_Database{
            return sInstance?:Room.databaseBuilder(
                context,
                Online_Study_Database::class.java,
                DB_NAME
            ).build().also { sInstance= it }
        }

    }

}




