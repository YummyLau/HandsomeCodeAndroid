package com.effective.android.base.list.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 列表基类
 * Created by yummyLau on 2018/5/8.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T data;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }
}
