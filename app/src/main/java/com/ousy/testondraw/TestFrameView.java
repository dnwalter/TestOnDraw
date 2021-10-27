package com.ousy.testondraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by ousiyuan on 2019/4/1 0001.
 * description:
 */
public class TestFrameView extends ConstraintLayout {

    private static final Paint paintMask = createMaskPaint();
    private View viewFrame;

    private static Paint createMaskPaint() {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        return paint;
    }

    public TestFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_rec_camera, this, true);

        initView();
    }

    private void initView() {
        viewFrame = findViewById(R.id.view_frame);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if (viewFrame != null) {
            int width = getWidth();
            int height = getHeight();

            canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

            //
            // source
            //
            //            canvas.saveLayer(0, 0, width, height, paintMask, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);

            //
            // frame
            //
            viewFrame.buildDrawingCache();
            Bitmap frame = viewFrame.getDrawingCache();
            if (frame != null) {
                canvas.drawBitmap(frame, viewFrame.getLeft(), viewFrame.getTop(), paintMask);
                canvas.restore();
            }

        } else {
            super.dispatchDraw(canvas);
        }
    }
}
