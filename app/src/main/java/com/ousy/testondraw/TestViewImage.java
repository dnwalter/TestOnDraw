package com.ousy.testondraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ousiyuan on 2019/3/11 0011.
 * description:
 */
public class TestViewImage extends ConstraintLayout {

    private Drawable mask;
    private TextView tvMessage;
    private ConstraintLayout clContent;

    private static final Paint paintMask = createMaskPaint();

    private static Paint createMaskPaint() {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        return paint;
    }

    public TestViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_e_redpocket_msg, this, true);
//        setWillNotDraw(false);
        mask= getResources().getDrawable(R.drawable.msgbakground_from);

        initView();
    }

    private void initView() {
        tvMessage = findViewById(R.id.msgview_tv_leave_message);
        clContent = findViewById(R.id.msgview_cl_content);
    }

    public void setTvMessage(){
        tvMessage.setText("adfdfasfd");
        clContent.setPadding(20, 0, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("ousyxx", "ondraw");
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

    @Override
    public void dispatchDraw(Canvas canvas) {
        Log.e("ousyxx", "ondisdraw");
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
            super.dispatchDraw(canvas);
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
    }
}
