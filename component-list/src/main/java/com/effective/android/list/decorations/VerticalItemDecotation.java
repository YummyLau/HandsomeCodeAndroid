package com.effective.android.list.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 垂直分割线
 * Created by yummyLau on 2018/8/14.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class VerticalItemDecotation extends RecyclerView.ItemDecoration {

    private int halfSpace;
    private boolean skinStart;
    private boolean skinEnd;

    public VerticalItemDecotation(int itemSpace) {
        this(itemSpace, false, false);
    }

    public VerticalItemDecotation(int itemSpace, boolean skinStart, boolean skipEnd) {
        this.halfSpace = itemSpace / 2;
        this.skinStart = skinStart;
        this.skinEnd = skipEnd;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        int position = parent.getChildLayoutPosition(view);
        if (position == 0) {
            outRect.top = skinStart ? 0 : 2 * halfSpace;
        } else if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = skinEnd ? 0 : 2 * halfSpace;
        }
    }
}
