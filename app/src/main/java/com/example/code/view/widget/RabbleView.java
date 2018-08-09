package com.example.code.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 刮奖效果
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class RabbleView extends AppCompatTextView {
    //要擦拭的背景
    private Bitmap mBitmap;
    //擦拭背景颜色
    private int bgColor = 0XFFCECECE;
    //擦拭画布
    private Canvas mCanvas;
    //擦拭画笔
    private Paint mPaint;

    //擦拭画笔宽度
//    private int mStrokeWidth = DisplayUtils.dp2px(getContext(), 40);
    private int mStrokeWidth = 80;
    //擦拭痕迹
    private Path mPath;
    private float mX, mY;
    private boolean isUp = false;

    public RabbleView(Context context) {
        this(context, null);
    }

    public RabbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RabbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRabble();
    }

    private void initRabble() {
        mPaint = new Paint();
        //透明
        mPaint.setAlpha(0);
        //抗锯齿
        mPaint.setDither(true);
        //防抖动
        mPaint.setAntiAlias(true);
        // 此处不能为透明色
        mPaint.setColor(Color.BLACK);
        //两图的相交模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setStyle(Paint.Style.STROKE);
        //设置结合处的样子，Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 前圆角
        //当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mStrokeWidth);
        // 痕迹
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //初始化擦拭背景
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(bgColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //要是宽度或者高度为0就没必要加上擦拭涂层了
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (!isUp) {
            mCanvas.drawPath(mPath, mPaint);
        } else {
            //当手指离开就把涂层变成透明的
            mBitmap.eraseColor(0X00);
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                down(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                move(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                up(event.getX(), event.getY());
                break;
        }
        return true;
    }

    private void down(float x, float y) {
        mPath.reset();
        mX = x;
        mY = y;
        mPath.moveTo(x, y);
    }

    private void move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= 3 || dy >= 3) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
//            mPath.lineTo(x,y);
            mX = x;
            mY = y;
        }
        invalidate();
    }

    private void up(float x, float y) {
        mPath.lineTo(x, y);
        mPath.reset();
        isUp = true;
        invalidate();
    }
}

