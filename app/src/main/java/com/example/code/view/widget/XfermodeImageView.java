package com.example.code.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.code.R;


/**
 * 遮罩处理图片
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class XfermodeImageView extends AppCompatImageView {

    private int type;
    private static final int TYPE_NONE = -1;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private Bitmap mSrc;
    private int mRadius;
    private int mTopRightRadius;
    private int mTopLeftRadius;
    private int mBottomRightRadius;
    private int mBottomLeftRadius;

    public XfermodeImageView(Context context) {
        this(context, null);
    }

    public XfermodeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        //获取自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XfermodeImageView);
        if (array != null) {
            type = array.getInt(R.styleable.XfermodeImageView_type, TYPE_NONE);
            mTopRightRadius = array.getDimensionPixelSize(R.styleable.XfermodeImageView_corner_radius_top_right, 0);
            mTopLeftRadius = array.getDimensionPixelSize(R.styleable.XfermodeImageView_corner_radius_top_left, 0);
            mBottomRightRadius = array.getDimensionPixelSize(R.styleable.XfermodeImageView_corner_radius_bottom_right, 0);
            mBottomLeftRadius = array.getDimensionPixelSize(R.styleable.XfermodeImageView_corner_radius_bottom_left, 0);
            mRadius = array.getDimensionPixelSize(R.styleable.XfermodeImageView_corner_radius, 0);

            int min = mTopRightRadius;
            if (mTopLeftRadius != 0 && mTopLeftRadius < min) {
                min = mTopLeftRadius;
            }

            if (mBottomRightRadius != 0 && mBottomRightRadius < min) {
                min = mBottomRightRadius;
            }

            if (mBottomLeftRadius != 0 && mBottomLeftRadius < min) {
                min = mBottomLeftRadius;
            }

            mRadius = min;
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() != null) {
            Bitmap bitmap = getBitmap(getDrawable());
            if (bitmap != null) {
                switch (type) {
                    case TYPE_CIRCLE:
                        //获取ImageView中的宽高，取最小值
                        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
                        //从当前存在的Bitmap，按一定的比例创建一个新的Bitmap。
                        mSrc = Bitmap.createScaledBitmap(bitmap, min, min, false);
                        canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                        break;
                    case TYPE_ROUND:
                        mSrc = Bitmap.createScaledBitmap(bitmap, getMeasuredWidth(), getMeasuredHeight(), false);
                        canvas.drawBitmap(createRoundCornerImage(mSrc), 0, 0, null);
                        break;
                }
            }
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else if (drawable instanceof TransitionDrawable) {
            int numIndex = ((TransitionDrawable) drawable).getNumberOfLayers();
            if (numIndex > 0) {
                return drawableToBitmap(((TransitionDrawable) drawable).getDrawable(numIndex - 1));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
//canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 绘制圆角
     *
     * @param source
     * @return
     */
    private Bitmap createRoundCornerImage(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        if (mTopLeftRadius == 0) {
            canvas.drawRect(0, 0, mRadius, mRadius, paint);
        }
        if (mTopRightRadius == 0) {
            canvas.drawRect(rect.right - mRadius, 0, rect.right, mRadius, paint);
        }
        if (mBottomLeftRadius == 0) {
            canvas.drawRect(0, rect.bottom - mRadius, mRadius, rect.bottom, paint);
        }
        if (mBottomRightRadius == 0) {
            canvas.drawRect(rect.right - mRadius, rect.bottom - mRadius, rect.right, rect.bottom,
                    paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 绘制圆形
     *
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
