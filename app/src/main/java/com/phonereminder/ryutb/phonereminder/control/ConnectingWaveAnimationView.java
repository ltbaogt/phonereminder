package com.phonereminder.ryutb.phonereminder.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ducth on 8/4/16.
 */
public class ConnectingWaveAnimationView extends View {

    private static final int RIPPLE_DURATION = 700;
    private static final int FRAME_RATE = 10;
    private int timer = 0;
    private int maxRadius = 0;
    private int cX, cY;

    private Paint paint;

    public ConnectingWaveAnimationView(Context context) {
        super(context);
        init();
    }

    public ConnectingWaveAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConnectingWaveAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#32F5F5F5"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cX = w / 2;
        cY = h / 2;
        maxRadius = Math.max(cX, cY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (RIPPLE_DURATION < timer * FRAME_RATE) {
            timer = 0;
        }

        paint.setAlpha((int) (255 - (255 * ((float) timer * FRAME_RATE) / RIPPLE_DURATION)));
        canvas.drawCircle(cX, cY, (maxRadius * (((float) timer * FRAME_RATE) / RIPPLE_DURATION)),
                paint);

        int distance = 10;
        if (timer >= distance) {
            paint.setAlpha((int) (255 - (255 * ((float) (timer - distance) * FRAME_RATE) / RIPPLE_DURATION)));
            canvas.drawCircle(cX, cY,
                    (maxRadius * (((float) (timer - distance) * FRAME_RATE) / RIPPLE_DURATION)),
                    paint);
        }

        distance = 30;
        if (timer >= distance) {
            paint.setAlpha((int) (255 - (255 * ((float) (timer - distance) * FRAME_RATE) / RIPPLE_DURATION)));
            canvas.drawCircle(cX, cY,
                    (maxRadius * (((float) (timer - distance) * FRAME_RATE) / RIPPLE_DURATION)),
                    paint);
        }

        timer++;

        invalidate();
    }
}