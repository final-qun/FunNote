package com.quicksand.funnote.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.quicksand.funnote.R;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class CircleImageView extends ImageView {
    private Paint mBitmapPaint;
    private Paint mBorderPaint;
    private int mWidth;
    private int mRadius;
    private float mStrokedWidth;
    private int mStrokedColor;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        initBitmapPaint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mStrokedWidth = typedArray.getDimension(R.styleable.CircleImageView_StrokeWidth,0);
        mStrokedColor = typedArray.getColor(R.styleable.CircleImageView_StrokeColor,Color.WHITE);
        typedArray.recycle();
        initBorderPaint();
    }

    private void initBitmapPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setColor(Color.RED);
    }

    private void initBorderPaint() {
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mStrokedColor);
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setStrokeWidth(mStrokedWidth);
        this.setLayerType(LAYER_TYPE_SOFTWARE, mBorderPaint);
        mBorderPaint.setShadowLayer(12.0f,3.0f,3.0f,Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mWidth = Math.min(width, height);
        setMeasuredDimension(mWidth, mWidth);
        mRadius = mWidth / 2;
        mRadius = (int)(mRadius - 2 * mStrokedWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setBitmapShader();
        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mBitmapPaint);
        //setBorderPaint();
        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mBorderPaint);
    }

    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int bitmapSize = Math.min(bitmap.getWidth(), bitmap.getHeight());

        scale = mWidth * 1.0f / bitmapSize;
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }
}
