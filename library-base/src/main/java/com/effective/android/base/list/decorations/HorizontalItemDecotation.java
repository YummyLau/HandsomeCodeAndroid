package com.effective.android.base.list.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 水平分割线
 * Created by yummyLau on 2018/8/14.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HorizontalItemDecotation extends RecyclerView.ItemDecoration {

    private int halfSpace;
    private boolean skinStart;
    private boolean skinEnd;

    public HorizontalItemDecotation(int itemSpace) {
        this(itemSpace, false, false);
    }

    public HorizontalItemDecotation(int itemSpace, boolean skinStart, boolean skipEnd) {
        this.halfSpace = itemSpace / 2;
        this.skinStart = skinStart;
        this.skinEnd = skipEnd;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = halfSpace;
        outRect.right = halfSpace;
        int position = parent.getChildLayoutPosition(view);
        if (position == 0) {
            outRect.left = skinStart ? 0 : 2 * halfSpace;
        } else if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.right = skinEnd ? 0 : 2 * halfSpace;
        }
    }
}
