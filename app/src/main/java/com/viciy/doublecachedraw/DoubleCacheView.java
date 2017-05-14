package com.viciy.doublecachedraw;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Baiqiang on 2017/5/10.
 */

public class DoubleCacheView extends View {

    private Paint mPaint;
    private Bitmap mBufferBitmap;
    private Canvas mMBufferCanvas;

    public DoubleCacheView(Context context) {
        this(context, null);
    }

    public DoubleCacheView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public DoubleCacheView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mBufferBitmap == null) {
                    mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    mMBufferCanvas = new Canvas(mBufferBitmap);
                }
                mMBufferCanvas.drawCircle(event.getX(), event.getY(), 5, mPaint);
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBufferBitmap == null) {
            return;
        }
        canvas.drawBitmap(mBufferBitmap, 0, 0, null);
    }
}
