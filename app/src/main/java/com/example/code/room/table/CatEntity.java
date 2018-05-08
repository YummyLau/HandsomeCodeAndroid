package com.example.code.room.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@Entity(
        tableName = CatEntity.TABLE_NAME,
        foreignKeys = @ForeignKey(entity = UserEntity.class,
                parentColumns = UserEntity.COL_ID,
                childColumns = CatEntity.COL_USER_ID)
)
public class CatEntity {

    public static final String TABLE_NAME = "cat_table";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_AGE = "age";
    public static final String COL_WEIGHT = "weight";

    @ColumnInfo(name = COL_USER_ID)
    public String uid;

    @ColumnInfo(name = CatEntity.COL_ID)
    public String id;

    @ColumnInfo(name = CatEntity.COL_NAME)
    public String name;

    @ColumnInfo(name = CatEntity.COL_AGE)
    public String age;

    @ColumnInfo(name = CatEntity.COL_WEIGHT)
    public String weight;
}
