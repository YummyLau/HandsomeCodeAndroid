package com.example.code.room.table;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AddressEntity {

    public static final String COL_CODE = "code";
    public static final String COL_STREET = "street";
    public static final String COL_STATE = "state";
    public static final String COL_CITY = "city";

    @ColumnInfo(name = COL_CODE)
    public long code;

    @ColumnInfo(name = COL_STREET)
    public String street;

    @ColumnInfo(name = COL_STATE)
    public String state;

    @ColumnInfo(name = COL_CITY)
    public String city;

}
