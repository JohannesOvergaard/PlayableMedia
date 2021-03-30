package com.example.skraldemonstre;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.mlkit.vision.objects.DetectedObject;
//Class adapted from: https://github.com/googlesamples/mlkit/blob/master/android/vision-quickstart/app/src/main/java/com/google/mlkit/vision/demo/java/objectdetector/ObjectGraphic.java
public class ObjectFrame extends GraphicOverlay.Graphic {
    private final DetectedObject object;

    ObjectFrame(GraphicOverlay overlay, DetectedObject object) {
        super(overlay);
        this.object = object;
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = new RectF(object.getBoundingBox());
        float x0 = translateX(rect.left);
        float x1 = translateY(rect.right);
        rect.left = Math.min(x0, x1);
        rect.right = Math.max(x0, x1);
        rect.top = translateY(rect.top);
        rect.bottom = translateY(rect.bottom);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);

        canvas.drawRect(rect, paint);
    }
}
