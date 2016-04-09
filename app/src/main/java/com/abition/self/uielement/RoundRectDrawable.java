package com.abition.self.uielement;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by KlousesSun on 16/4/5.
 */
public class RoundRectDrawable extends Drawable {
    Paint mPaint;
    RectF mBounds;

    public RoundRectDrawable(int color) {
        super();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBounds = new RectF(bounds);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mBounds, 12f, 12f, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0xcd;
    }
}
