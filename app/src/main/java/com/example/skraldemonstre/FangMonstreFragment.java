package com.example.skraldemonstre;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.util.List;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

public class FangMonstreFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CAMERA_PERMISSION_CODE = 100;
    private ImageView trash_image;
    private Bitmap _rawImage;
    private GraphicOverlay overlay;
    private TextView trash_text;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fang_monstre, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FangMonstreFragment.this)
                        .navigate(R.id.action_FangMonstreFragment_to_StartFragment);
            }
        });

        view.findViewById(R.id.button_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //triggers camera functionality
                overlay.clear();
                trash_image.setImageBitmap(null);
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }

        });

        trash_image = view.findViewById(R.id.trash_image);
        overlay = view.findViewById(R.id.graphic_overlay);
        trash_text = view.findViewById(R.id.trash_fact);

    }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            //Request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            dispatchCaptureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchCaptureIntent();
            }
        }
    }

    private void dispatchCaptureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            //TODO: error handling
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            trash_image.setImageBitmap(image);
            processImage(image);
            detectItem(image);
            _rawImage = image;
        }
    }



    public ImageLabeler setupImageLabelling() {
        LocalModel localModel = new LocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build();

        CustomImageLabelerOptions options = new CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.7f)
                .build();

        return ImageLabeling.getClient(options);
    }

    public ObjectDetectionCustomProcessor setupObjectDetection() {
        ObjectDetectorOptions options = new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .build();

        return new ObjectDetectionCustomProcessor(this.getContext(), options);
    }

    public void processImage(Bitmap image) {

        InputImage inputImage = InputImage.fromBitmap(image, 0);

        ImageLabeler labeler = setupImageLabelling();

        //Label image
        labeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        //Items recognized
                        if (imageLabels.size() == 0) {
                            trash_text.append("Det ved jeg ikke hvad er."); //TODO: bedre tekst?
                        }
                        for (ImageLabel l : imageLabels) {
                            trash_text.append(l.getText() + " : " + l.getConfidence());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Items not recognized
                        trash_text.append("Det ved jeg ikke hvad er.");
                    }
                });
    }

    public void detectItem(Bitmap image) {
        InputImage inputImage = InputImage.fromBitmap(image, 0);
        ObjectDetectionCustomProcessor detector = setupObjectDetection();

        overlay.clear();

        float scale = Math.max((float) image.getWidth() / (float) trash_image.getWidth(),
                (float) image.getHeight() / (float) trash_image.getHeight());

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, (int) (image.getWidth() / scale), (int) (image.getHeight() / scale), true);

        trash_image.setImageBitmap(resizedBitmap);

        //TODO: tag en beslutning - skal den prøve at sætte ansigt på selvom den ikke genkender typen af skrald?
        overlay.setImageSourceInfo(
                resizedBitmap.getWidth(), resizedBitmap.getHeight(), false);

        detector.processBitmap(resizedBitmap, overlay);

    }

}