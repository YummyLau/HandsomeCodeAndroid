package com.example.code.room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.code.room.table.UserEntity;

import java.util.List;

/**
 * 用户Dao
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 *
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table WHERE id = :id")
    List<UserEntity> query(String id);

    @Query("SELECT * FROM user_table WHERE id IN (:ids)")
    List<UserEntity> query(String[] ids);

    @Query("SELECT * FROM user_table")
    List<UserEntity> queryAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<UserEntity>> queryAll2();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity userEntity);

    @Insert
    void insert(UserEntity... userEntities);

    @Delete
    void delete(UserEntity userEntity);

    @Delete
    void delete(UserEntity... userEntities);


}
