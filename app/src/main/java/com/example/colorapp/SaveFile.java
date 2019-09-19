package com.example.colorapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SaveFile {

    //method for save the image
    public static File saveFile(Activity myActivity, Bitmap bitmap) throws IOException {//that method return type is file

        //check weather it has media storage or not
        String externalStorageState = Environment.getExternalStorageState();

        File myFile = null;

        if(externalStorageState.equals(Environment.MEDIA_MOUNTED)) {

            File picturesDirectory = myActivity.getExternalFilesDir("ColorAppPictures");
            Date currentDate = new Date();
            long elapsedTime = SystemClock.elapsedRealtime(); //return the time since the system was booted
            String uniqueImageName = "/" + currentDate + "_" + elapsedTime + ".png";

            //Also create a new file accourding to the new picture
            myFile = new File(picturesDirectory + uniqueImageName);

            //then check weather device has external storage or not
            long remainingSpace = picturesDirectory.getFreeSpace(); // that return the free sapce with byte

            //return the storage for store the picture in the memory
            long requiredSpace = bitmap.getByteCount();

            if(requiredSpace * 1.8 < remainingSpace) {

                try {

                    FileOutputStream fileOutputStream = new FileOutputStream(myFile);
                    boolean isImageSaveSuccessfully = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    if(isImageSaveSuccessfully) {

                        return myFile;

                    } else {

                        throw new IOException("This image is not saved successfully in to the External Storage.");

                    }



                } catch (Exception e) {

                    throw new IOException("The operation of saving the Image to External Storage wen wrong.");
                }

            } else {

                throw new IOException("There is no enough space in order to save the image to External Storage.");

            }



        } else {

            throw new IOException("This device does not have External Storage.");

        }

    }

}
