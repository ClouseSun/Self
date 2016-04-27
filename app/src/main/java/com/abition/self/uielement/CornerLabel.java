package com.abition.self.uielement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by KlousesSun on 16/4/27.
 */
public class CornerLabel extends View {

    Path trianglePath;
    Paint paint;

    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    public CornerLabel(Context context) {
        super(context, null);
    }

    public CornerLabel(Context context, AttributeSet attrs) {
        super(context, attrs);

        trianglePath = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public CornerLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        trianglePath.reset();
        trianglePath.moveTo(0, 0);
        trianglePath.lineTo(h, w);
        trianglePath.lineTo(w, 0);
        trianglePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(trianglePath, paint);
    }
}
