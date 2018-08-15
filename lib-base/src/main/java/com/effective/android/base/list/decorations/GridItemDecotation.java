package com.effective.android.base.list.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * grid分割线
 * Created by yummyLau on 2018/8/14.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class GridItemDecotation extends RecyclerView.ItemDecoration {

    private int middlePadding;
    private int edgePadding;
    private Paint paint;

    public GridItemDecotation(Context context, int middlePadding, int color) {
        this(context, middlePadding, 0, color);
    }

    public GridItemDecotation(Context context, int middlePadding, int edgePadding, int color) {
        this.middlePadding = middlePadding;
        this.edgePadding = edgePadding;
        paint = new Paint();
        paint.setColor(context.getResources().getColor(color));
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int spanCount = ((GridLayoutManager) manager).getSpanCount();// must be GridLayout Manager
        final int childCount = parent.getChildCount();
        if (((GridLayoutManager) manager).getOrientation() == GridLayoutManager.VERTICAL) {
            onDrawVerticalDivider(c, parent, spanCount, childCount);
        } else {
            onDrawHorizontalDivider(c, parent, spanCount, childCount);
        }
    }

    private void onDrawHorizontalDivider(Canvas c, RecyclerView parent, int spanCount, int childCount) {
        int allCol = (parent.getAdapter().getItemCount() + spanCount - 1) / spanCount;//ceil
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int left = child.getLeft();
            final int right = child.getRight();
            final int top = child.getTop();
            final int bottom = child.getBottom();

            boolean lastLine = (i % spanCount) == (spanCount - 1);
            if ((i / spanCount + 1) != allCol) {// last allCol not deed divider
                if (lastLine) {
                    c.drawRect(right, top, right + middlePadding, bottom, paint);
                } else {
                    c.drawRect(right, top, right + middlePadding, bottom + middlePadding, paint);
                }
            }
            if (!lastLine) {
                c.drawRect(left, bottom, right, bottom + middlePadding, paint);
            }
        }
    }

    private void onDrawVerticalDivider(Canvas c, RecyclerView parent, int spanCount, int childCount) {
        int allRow = (parent.getAdapter().getItemCount() + spanCount - 1) / spanCount;// ceil
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int left = child.getLeft();
            final int right = child.getRight();
            final int top = child.getTop();
            final int bottom = child.getBottom();

            boolean lastLine = (i / spanCount + 1) == allRow;
            if (!lastLine) {
                c.drawRect(left, bottom, right, bottom + middlePadding, paint);
            }
            if ((i % spanCount) != (spanCount - 1)) {
                if (lastLine) {
                    c.drawRect(right, top, right + middlePadding, bottom, paint);
                } else {
                    c.drawRect(right, top, right + middlePadding, bottom + middlePadding, paint);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (manager instanceof GridLayoutManager) {
            getGridOffset(((GridLayoutManager) manager).getOrientation(), ((GridLayoutManager) manager).getSpanCount
                    (), outRect, childPosition, itemCount);
        }
    }

    /**
     * 设置GridLayoutManager 类型的 offest
     *
     * @param orientation   方向
     * @param spanCount     个数
     * @param outRect       padding
     * @param childPosition 在 list 中的 postion
     * @param itemCount     list size
     */
    private void getGridOffset(int orientation, int spanCount, Rect outRect, int childPosition, int itemCount) {
        float totalPadding = middlePadding * (spanCount - 1) + edgePadding * 2; // 总共的padding值
        float eachPadding = totalPadding / spanCount; // 分配给每个item的padding值
        int column = childPosition % spanCount; // 列数
        int row = childPosition / spanCount;// 行数
        float left;
        float right;
        float top;
        float bottom;
        if (orientation == GridLayoutManager.VERTICAL) {
            top = 0; // 默认 top为0
            bottom = middlePadding; // 默认bottom为间距值
            if (edgePadding == 0) {
                left = column * eachPadding / (spanCount - 1);
                right = eachPadding - left;
                // 无边距的话  只有最后一行bottom为0
                if (itemCount / spanCount == row) {
                    bottom = 0;
                }
            } else {
                if (childPosition < spanCount) {
                    // 有边距的话 第一行top为边距值
                    top = edgePadding;
                } else if (itemCount / spanCount == row) {
                    // 有边距的话 最后一行bottom为边距值
                    bottom = edgePadding;
                }
                left = column * (eachPadding - edgePadding - edgePadding) / (spanCount - 1) + edgePadding;
                right = eachPadding - left;
            }
        } else {
            // orientation == GridLayoutManager.HORIZONTAL 跟上面的大同小异, 将top,bottom替换为left,right即可
            left = 0;
            right = middlePadding;
            if (edgePadding == 0) {
                top = column * eachPadding / (spanCount - 1);
                bottom = eachPadding - top;
                if (itemCount / spanCount == row) {
                    right = 0;
                }
            } else {
                if (childPosition < spanCount) {
                    left = edgePadding;
                } else if (itemCount / spanCount == row) {
                    right = edgePadding;
                }
                top = column * (eachPadding - edgePadding - edgePadding) / (spanCount - 1) + edgePadding;
                bottom = eachPadding - top;
            }
        }
        outRect.set((int) left, (int) top, (int) right, (int) bottom);
    }
}

