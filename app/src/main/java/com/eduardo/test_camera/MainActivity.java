package com.eduardo.test_camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;import android.hardware.Camera;
import android.view.View;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity
{
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private static String tag = "TEST_CAMERA";
    private static Camera camera;
    private Button button_takePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_takePicture = (Button)findViewById(R.id.button_takePicture);

        camera = null;

        if (checkCameraHardware(MainActivity.this))
        {
            Camera camera = getCameraInstance();
        }

        button_takePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                takePicture();
            }
        });

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        CameraPreview cameraPreview = (CameraPreview) findViewById(R.id.cameraPreview);
        cameraPreview.releaseCamera();

        if (camera != null)
            camera.release();
    }

    private void takePicture()
    {
        camera.takePicture(null, null, new Camera.PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                onPictureJpeg(data, camera);
            }
        });
    }

    private void onPictureJpeg(byte[] bytes, Camera camera)
    {
        // Save picture to file
        File file = CameraHelper.generateTimestampPhotoFile();

        try
        {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            Log.i(tag, "File saved at " + file.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        // restart the preview. the preview stops whenever you take a picture and save the file
        camera.startPreview();
    }

    // Check if device has a camera
    private boolean checkCameraHardware(Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Log.i(tag, "Device has camera");
            return true;
        }
        else
        {
            Log.i(tag, "No camera found");
            return false;
        }
    }//end checkCameraHardware

    // This is a safe way to get an instance of the Camera object
    public Camera getCameraInstance()
    {
        try
        {
            camera = Camera.open();  // attempt to get a camera instance

            // Get camera preview
            CameraPreview cameraPreview = (CameraPreview) findViewById(R.id.cameraPreview);
            cameraPreview.connectCamera(camera);

        }
        catch (Exception e)
        {
            Log.e(tag, "Camera is not available");
            Log.e(tag, e.getLocalizedMessage());
        }
        return camera;   // returns null if the camera is not available
    }//end getCameraInstance


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}//end MainActivity


































