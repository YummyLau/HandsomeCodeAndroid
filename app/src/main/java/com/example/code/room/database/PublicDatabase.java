package com.example.code.room.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.example.code.room.Dao.UserDao;
import com.example.code.room.table.UserEntity;

/**
 * created by g8931 on 2018/5/3
 */
@Database(entities = {
        UserEntity.class
}, version = 1)
public abstract class PublicDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
