package com.example.skraldemonstre;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import com.google.mlkit.vision.objects.DetectedObject;
import java.util.Random;

//Class adapted from: https://github.com/googlesamples/mlkit/blob/master/android/vision-quickstart/app/src/main/java/com/google/mlkit/vision/demo/java/objectdetector/ObjectGraphic.java
public class ObjectFrame extends GraphicOverlay.Graphic {
    private final DetectedObject object;

    ObjectFrame(GraphicOverlay overlay, DetectedObject object) {
        super(overlay);
        this.object = object;
    }

    @Override
    public void draw(Canvas canvas) {
        RectF topRect = new RectF(object.getBoundingBox());
        float x0 = translateX(topRect.left);
        float x1 = translateY(topRect.right);
        topRect.left = Math.min(x0, x1);
        topRect.right = Math.max(x0, x1);
        topRect.top = translateY(topRect.top);
        topRect.bottom = translateY(topRect.top+((topRect.bottom-topRect.top)/2)); //Top half of rect

        RectF bottomRect = new RectF(object.getBoundingBox());
        float x2 = translateX(bottomRect.left);
        float x3 = translateY(bottomRect.right);
        bottomRect.left = Math.min(x2, x3);
        bottomRect.right = Math.max(x2, x3);
        bottomRect.top = translateY(bottomRect.bottom-((topRect.bottom-topRect.top)/2)); //bottom half of rect
        bottomRect.bottom = translateY(bottomRect.bottom);

        Random random = new Random();

        Resources res = getApplicationContext().getResources();
        canvas.drawBitmap(BitmapFactory.decodeResource(res,
                mEyesIds[random.nextInt(mEyesIds.length)]),null,topRect,null);
        canvas.drawBitmap(BitmapFactory.decodeResource(res,
                mMouthIds[random.nextInt(mMouthIds.length)]),null,bottomRect,null);
    }

    public Integer[] mEyesIds = {
            R.drawable.eyes1,
            R.drawable.eyes2,
            R.drawable.eyes3,
            R.drawable.eyes4,
            R.drawable.eyes5,
            R.drawable.eyes6
    };

    public Integer[] mMouthIds = {
            R.drawable.mouth1,
            R.drawable.mouth2,
            R.drawable.mouth3,
            R.drawable.mouth4,
            R.drawable.mouth5,
            R.drawable.mouth6,
            R.drawable.mouth7,
            R.drawable.mouth8,
            R.drawable.mouth9
    };
}
