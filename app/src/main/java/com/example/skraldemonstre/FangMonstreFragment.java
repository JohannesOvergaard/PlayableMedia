package com.example.skraldemonstre;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

public class FangMonstreFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CAMERA_PERMISSION_CODE = 100;
    private ImageView trash_image;
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
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }

        });

        trash_image = view.findViewById(R.id.trash_image);
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


    public void processImage(Bitmap image) {

        /*int rotation = 0;
        try {
            rotation = getRotation(getActivity(), getContext());
        } catch (CameraAccessException a) {
            //TODO: Do something
        }*/

        InputImage inputImage = InputImage.fromBitmap(image, 0);

        ImageLabeler labeler = setupImageLabelling();
        labeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        for (ImageLabel l : imageLabels) {
                            trash_text.append(l.getText() + " : " + l.getConfidence());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO: handle failure
                    }
                });
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
        static {
            ORIENTATIONS.append(Surface.ROTATION_0, 90);
            ORIENTATIONS.append(Surface.ROTATION_90, 0);
            ORIENTATIONS.append(Surface.ROTATION_180, 270);
            ORIENTATIONS.append(Surface.ROTATION_270, 180);
        }

    /*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotation(Activity activity, Context context) throws CameraAccessException {
         //Get angle for image rotation based on device's current rotation
            int devicerotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            int rotationCompensation = ORIENTATIONS.get(devicerotation);

            CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
            String[] ids = cameraManager.getCameraIdList();
            String cameraId = ids[0]; //assuming first camera is being used?
            int sensorOrientation = cameraManager
                    .getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.SENSOR_ORIENTATION);
            rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

            int result;
            switch (rotationCompensation) {
                case 0:
                    result = Surface.ROTATION_0;
                    break;
                case 90:
                    result = Surface.ROTATION_90;
                    break;
                case 180:
                    result = Surface.ROTATION_180;
                    break;
                case 270:
                    result = Surface.ROTATION_270;
                    break;
                default:
                    result = Surface.ROTATION_0;
            }
            return result;
    }*/
}