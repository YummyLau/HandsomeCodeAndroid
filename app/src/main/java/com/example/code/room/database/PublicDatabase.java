package com.example.code.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.code.room.Dao.UserDao;
import com.example.code.room.table.UserEntity;

/**
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@Database(entities = {
        UserEntity.class
}, version = 1)
public abstract class PublicDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
