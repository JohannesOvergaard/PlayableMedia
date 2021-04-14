package com.example.skraldemonstre;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase;

import java.util.List;

//Code in this class adapted from: https://github.com/googlesamples/mlkit/blob/master/android/vision-quickstart/app/src/main/java/com/google/mlkit/vision/demo/java/objectdetector/ObjectDetectorProcessor.java

public class ObjectDetectionCustomProcessor extends VisionProcessorBase<List<DetectedObject>> {

        private static final String TAG = "ObjectDetectorProcessor";

        private final ObjectDetector detector;

        public ObjectDetectionCustomProcessor(Context context, ObjectDetectorOptionsBase options) {
            super(context);
            detector = ObjectDetection.getClient(options);
        }

        @Override
        public void stop() {
            super.stop();
            detector.close();
        }

        @Override
        protected Task<List<DetectedObject>> detectInImage(InputImage image) {
            return detector.process(image);
        }

        @Override
        protected void onSuccess(
                @NonNull List<DetectedObject> results, @NonNull GraphicOverlay graphicOverlay) {
            for (DetectedObject object : results) {
                graphicOverlay.add(new ObjectFrame(graphicOverlay, object));
            }
        }

        @Override
        protected void onFailure(@NonNull Exception e) {
            Log.e(TAG, "Object detection failed!", e);
        }
    }

