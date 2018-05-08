package com.example.code.room.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@Entity(
        tableName = UserEntity.TABLE_NAME,
        indices = {@Index(value = UserEntity.COL_ID, unique = true), @Index(value = {UserEntity.COL_NAME, UserEntity.COL_AGE}, unique = true)}
)
public class UserEntity {

    public static final String TABLE_NAME = "user_table";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_AGE = "age";
    public static final String COL_CREATE_TIME = "create_time";

    @PrimaryKey
    @ColumnInfo(name = COL_ID)
    public String id;

    @ColumnInfo(name = COL_NAME)
    public String name;

    @ColumnInfo(name = COL_AGE)
    public int age;

    @ColumnInfo(name = COL_CREATE_TIME)
    public long createTime;

    @Embedded(prefix = "first_")
    public AddressEntity firstHome;

    @Embedded(prefix = "second_")
    public AddressEntity secondHome;

    @Ignore
    public Bitmap picture;
}
