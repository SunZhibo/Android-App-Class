package com.example.camerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.DragAndDropPermissionsCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    final int PERMISSION_REQUEST_CODE = 100;
    final int ACTIVITY_REQUEST_CODE = 200;
    final String TAG = "VIDEOPATH";

    private CameraView mPreview;
    private Camera mCamera;
    private Button mButton;
    private FrameLayout mpreview;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording;
    private String videoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        isRecording = false;
        mButton = findViewById(R.id.button_video);
        mpreview = findViewById(R.id.camera_view);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        intiCameraView();
        initButton();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mCamera == null) {
            initCamera();
        }
        mCamera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    private void intiCameraView() {
        initCamera();
        mPreview = new CameraView(this, mCamera);
        mpreview.addView(mPreview);
    }

    private void initCamera() {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
    }

    private void initButton() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecording) {
                    if (prepareVideoRecorder()) {
                        mMediaRecorder.start();
                        mButton.setText("Stop");
                        isRecording = true;
                    }
                    else {
                        releaseMediaRecorder();
                    }
                }
                else {
                    mMediaRecorder.stop();
                    releaseMediaRecorder();
                    mCamera.lock();
                    mButton.setText("TAKE VIDEO");
                    isRecording = false;
                    Intent intent = new Intent(CameraActivity.this, VideoPlayActivity.class);
                    intent.putExtra(TAG, videoPath);
                    startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
                }
            }
        });
    }

    private String getOutputMediaPath() {
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir, "VEDIO_" + timeStamp + ".mp4");
        if (!mediaFile.exists()) {
            mediaFile.getParentFile().mkdirs();
        }
        return mediaFile.getAbsolutePath();
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();
        }
    }

    private boolean prepareVideoRecorder() {
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        videoPath = getOutputMediaPath();
        mMediaRecorder.setOutputFile(videoPath);
        mMediaRecorder.setPreviewDisplay(mPreview.mHolder.getSurface());
        mMediaRecorder.setOrientationHint(90);
        try {
            mMediaRecorder.prepare();
        } catch (Exception e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent tmpData = new Intent();
            tmpData.putExtra(TAG, videoPath);
            setResult(1, tmpData);
            finish();
        }
    }
}
