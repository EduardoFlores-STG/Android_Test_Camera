package com.eduardo.test_camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by Eduardo on 3/16/15.
 * A basic camera preview class
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder _holder;
    private Camera _camera;

    public CameraPreview(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }

    public CameraPreview(Context context)
    {
        super(context);
    }

    public void connectCamera(Camera camera)
    {
        _camera = camera;

        _camera.setDisplayOrientation(getCameraPreviewOrientation());

        _holder = getHolder();
        _holder.addCallback(this);

        // Start the preview

    }

    public void releaseCamera()
    {
        if (_camera != null)
        {
            _camera.release();
            // Stop preview
            stopPreview();

            _camera = null;

        }


    }

    public void startPreview()
    {
        if (_camera != null && _holder.getSurface() != null)
        {
            try
            {
                _camera.setPreviewDisplay(_holder);
                _camera.startPreview();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void stopPreview()
    {
        if (_camera != null)
            try
            {
                _camera.stopPreview();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        stopPreview();
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        stopPreview();
    }

    public int getDeviceOrientationDegrees()
    {
        int degrees = 0;

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int rotation = windowManager.getDefaultDisplay().getRotation();

        switch (rotation)
        {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        return degrees;
    }

    public int getCameraPreviewOrientation()
    {
        final int DEGREES_IN_CIRCLE = 360;
        int temp = 0;
        int previewOrientation = 0;

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0, cameraInfo);

        int deviceOrientation = getDeviceOrientationDegrees();

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
        {
            temp = cameraInfo.orientation - deviceOrientation + DEGREES_IN_CIRCLE;
            previewOrientation = temp % DEGREES_IN_CIRCLE;  // 0 to 270
        }

        return previewOrientation;
    }
}//end class CameraPreview



























