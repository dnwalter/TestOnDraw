package com.ousy.testondraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class FrameImageView extends View {
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;
    private float mStrokeWidth;
    private int mRadius;

    public FrameImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        srcBmp = makeSrc(width, height);
        dstBmp = makeDst(width, height);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mStrokeWidth = dip2px(context, 0.5f);
        mRadius = dip2px(context, 18);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        srcBmp = makeSrc(width, height);
        dstBmp = makeDst(width, height);

        canvas.drawColor(Color.GREEN);

        int layerID = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp, 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

        int layerID2 = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(new RectF(width / 2, 0, width, height / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawRoundRect(new RectF(mStrokeWidth, mStrokeWidth, width - mStrokeWidth, height - mStrokeWidth), mRadius - dip2px(getContext(), 0.5f), mRadius - dip2px(getContext(), 0.5f), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawRect(new RectF(width / 2, mStrokeWidth, width - mStrokeWidth, height / 2), paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerID2);


        if(srcBmp != null && !srcBmp.isRecycled()){
            srcBmp.recycle();
            srcBmp = null;
        }
        if(dstBmp != null && !dstBmp.isRecycled()){
            dstBmp.recycle();
            dstBmp = null;
        }
    }

    // create a bitmap with a circle, used for the "dst" image
    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawRoundRect(new RectF(0, 0, w, h), mRadius, mRadius, p);
        c.drawRect(new RectF(w / 2, 0, w, h / 2), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = ((BitmapDrawable) getContext().getResources().getDrawable(R.drawable.test_icon)).getBitmap();
        return bm;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
