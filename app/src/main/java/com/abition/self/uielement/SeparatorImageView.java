package com.abition.self.uielement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by KlousesSun on 16/4/9.
 */
public class SeparatorImageView extends ImageView {
    Paint paint;
    public SeparatorImageView(Context context) {
        this(context, null);
    }

    public SeparatorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStrokeWidth(2f);
        paint.setColor(0xffCDCDCD);
    }

    public SeparatorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int height;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(width / 2, 0, width / 2, height, paint);
    }
}
