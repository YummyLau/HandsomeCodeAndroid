package com.example.code.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.code.R;

/**
 * 圆形头像
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class CircleImageView extends AppCompatImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;                              //显示模式
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;                     //一个像素4个字节

    private static final int DEFAULT_BORDER_WIDTH = 0;                                              //默认无边框
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;                                    //边框颜色为黑色
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;                                //填充底色为透明
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private int borderColor = DEFAULT_BORDER_COLOR;
    private int borderWidth = DEFAULT_BORDER_WIDTH;
    private int fillColor = DEFAULT_FILL_COLOR;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean borderOverlay;

    private Bitmap sourceBitmap;
    private BitmapShader bitmapShader;
    private int generateBitmapWidth;
    private int generateBitmapHeight;

    private ColorFilter colorFilter;

    private float drawableRadius;
    private float borderRadius;

    private final RectF drawableRect = new RectF();
    private final RectF borderRect = new RectF();

    private final Matrix shaderMatrix = new Matrix();
    private final Paint bitmapPaint = new Paint();
    private final Paint borderPaint = new Paint();
    private final Paint fillPaint = new Paint();


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH);
        borderColor = a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR);
        borderOverlay = a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY);
        fillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR);
        a.recycle();

        super.setScaleType(SCALE_TYPE);
        mReady = true;
        if (mSetupPending) {
            reSet();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (sourceBitmap == null) {
            return;
        }

        if (fillColor != Color.TRANSPARENT) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, drawableRadius, fillPaint);
        }
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, drawableRadius, bitmapPaint);

        if (borderWidth != 0) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, borderRadius, borderPaint);
        }
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(@ColorInt int borderColorInt) {
        if (borderColorInt == this.borderColor) {
            return;
        }
        this.borderColor = borderColorInt;
        this.borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setBorderColorResource(@ColorRes int borderColorRes) {
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == this.fillColor) {
            return;
        }
        this.fillColor = fillColor;
        this.fillPaint.setColor(fillColor);
        invalidate();
    }

    public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == this.borderWidth) {
            return;
        }
        this.borderWidth = borderWidth;
        reSet();
    }

    public boolean isBorderOverlay() {
        return borderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == this.borderOverlay) {
            return;
        }
        this.borderOverlay = borderOverlay;
        reSet();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        sourceBitmap = bm;
        reSet();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        sourceBitmap = bitmap2Drawable(drawable);
        reSet();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        sourceBitmap = bitmap2Drawable(getDrawable());
        reSet();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        sourceBitmap = uri != null ? bitmap2Drawable(getDrawable()) : null;
        reSet();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == this.colorFilter) {
            return;
        }
        this.colorFilter = cf;
        bitmapPaint.setColorFilter(this.colorFilter);
        invalidate();
    }

    private Bitmap bitmap2Drawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void reSet() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (sourceBitmap == null) {
            invalidate();
            return;
        }

        bitmapShader = new BitmapShader(sourceBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setShader(bitmapShader);

        //border
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        //fill填充
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);

        generateBitmapHeight = sourceBitmap.getHeight();
        generateBitmapWidth = sourceBitmap.getWidth();

        //取圆形区域
        borderRect.set(0, 0, getWidth(), getHeight());
        borderRadius = Math.min((borderRect.height() - borderWidth) / 2.0f, (borderRect.width() - borderWidth) / 2.0f);

        drawableRect.set(borderRect);

        //如果border不需要覆盖，则内框缩进 borderWidth 距离
        if (!borderOverlay) {
            drawableRect.inset(borderWidth, borderWidth);
        }
        drawableRadius = Math.min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f);           //有多大画多大

        float scale;            //是否需要缩放
        float dx = 0;
        float dy = 0;

        shaderMatrix.set(null);

        if (generateBitmapWidth * drawableRect.height() > drawableRect.width() * generateBitmapHeight) {
            scale = drawableRect.height() / (float) generateBitmapHeight;
            dx = (drawableRect.width() - generateBitmapWidth * scale) * 0.5f;
        } else {
            scale = drawableRect.width() / (float) generateBitmapWidth;
            dy = (drawableRect.height() - generateBitmapHeight * scale) * 0.5f;
        }

        shaderMatrix.setScale(scale, scale);
        shaderMatrix.postTranslate((int) (dx + 0.5f) + drawableRect.left, (int) (dy + 0.5f) + drawableRect.top);

        bitmapShader.setLocalMatrix(shaderMatrix);
        invalidate();
    }
}
