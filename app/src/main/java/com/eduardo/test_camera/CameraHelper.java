package com.eduardo.test_camera;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduardo on 3/16/15.
 */
public class CameraHelper
{
    public static File getPhotoDirectory()
    {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();

        if (externalStorageState.equals(Environment.MEDIA_MOUNTED))
        {
            File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(picturesDir, "Eduardo_TestCamera");

            if ( !outputDir.exists())
            {
                if ( !outputDir.mkdirs())
                {
                    Log.e("CAMERA_HELPER", "Unable to create output directory");
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }

    public static Uri generateTimestampPhotoFileUri()
    {
        File photoFile = null;
        Uri photoFileUri = null;

        File outputDir = getPhotoDirectory();

        if (outputDir != null)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String photoFileName = "IMG_" + timeStamp + ".jpg";

            photoFile = new File(outputDir, photoFileName);
            photoFileUri = Uri.fromFile(photoFile);
        }

        return photoFileUri;
    }

    public static File generateTimestampPhotoFile()
    {
        File photoFile = null;

        File outputDir = getPhotoDirectory();

        if (outputDir != null)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String photoFileName = "IMG_" + timeStamp + ".jpg";

            photoFile = new File(outputDir, photoFileName);
        }

        return photoFile;
    }

}//end CameraHelper









































