package com.ousy.testondraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ousiyuan on 2019/3/11 0011.
 * description:
 */
public class TestImage extends android.support.v7.widget.AppCompatImageView {

    private Drawable mask;
    private Drawable maskBefore;

    private static final Paint paintMask = createMaskPaint();

    private static Paint createMaskPaint() {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        return paint;
    }

    public TestImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mask= getResources().getDrawable(R.drawable.msgbakground_from);
        maskBefore= getResources().getDrawable(R.drawable.msgbakground_from_pic);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mask != null) {
            int width = getWidth();
            int height = getHeight();

            canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

            //
            // mask
            //
            if (mask != null) {
                mask.setBounds(0, 0, width, height);
                mask.draw(canvas);
            }

            //
            // source
            //
            canvas.saveLayer(0, 0, width, height, paintMask, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }
}
