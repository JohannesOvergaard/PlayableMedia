package com.example.skraldemonstre;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//Code in this class borrowed from here: https://github.com/googlesamples/mlkit/blob/master/android/vision-quickstart/app/src/main/java/com/google/mlkit/vision/demo/CameraImageGraphic.java
public class CameraImageGraphic extends GraphicOverlay.Graphic {
    private final Bitmap bitmap;
    public CameraImageGraphic(GraphicOverlay overlay, Bitmap bitmap) {
        super(overlay);
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getTransformationMatrix(), null);
    }

}
