package com.example.colorapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTakePicture;
    private Button btnSavePicture;
    private Button btnShare;
    private ImageView imgPhoto;
    private SeekBar redColorSeekBar;
    private SeekBar greenColorSeekBar;
    private SeekBar blueColorSeekBar;
    private TextView txtRedColorValue;
    private TextView txtGreenColorValue;
    private TextView txtBlueColorValue;

    private static final int CAMERA_IMAGE_REQUEST_CODE = 1000;
    //unique value use

    private Bitmap bitmap;

    private Colorful colorful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnSavePicture = (Button) findViewById(R.id.btnSavePicture);
        btnShare = (Button) findViewById(R.id.btnShare);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        redColorSeekBar = (SeekBar) findViewById(R.id.redColorSeekBar);
        greenColorSeekBar = (SeekBar) findViewById(R.id.greenColorSeekBar);
        blueColorSeekBar = (SeekBar) findViewById(R.id.blueColorSeekBar);
        txtRedColorValue = (TextView) findViewById(R.id.txtRedColorValue);
        txtGreenColorValue = (TextView) findViewById(R.id.txtGreenColorValue);
        txtBlueColorValue = (TextView) findViewById(R.id.txtBlueColorValue);

        btnTakePicture.setOnClickListener(MainActivity.this);
        btnSavePicture.setOnClickListener(MainActivity.this);

        ColorizationHandler colorizationHandler = new ColorizationHandler();

        redColorSeekBar.setOnSeekBarChangeListener(colorizationHandler);
        greenColorSeekBar.setOnSeekBarChangeListener(colorizationHandler);
        blueColorSeekBar.setOnSeekBarChangeListener(colorizationHandler);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnTakePicture ) {

            //get the Camera permission to access
            int permissionResult = ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.CAMERA);
            if(permissionResult == PackageManager.PERMISSION_GRANTED) {

                // camera have or haven't in the device
                PackageManager packageManager = getPackageManager();

                //If device has camera or that work properly
                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //that intent need to access the camera store and capture an image

                    startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST_CODE);
                }
                else {

                    Toast.makeText(MainActivity.this, "Your device doesn't have a camera.",Toast.LENGTH_SHORT).show();

                }

            } else {
                //hold to permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA},1);

            }
        } else if(view.getId() == R.id.btnSavePicture) {

            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //these means user allow to access the application to external storage

                try {

                    SaveFile.saveFile(MainActivity.this, bitmap);
                    Toast.makeText(MainActivity.this, "The Image is successfully saved to External Storage.",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);

            }

        } else if (view.getId() == R.id.btnShare) {

            try {

                File myPictureFile = SaveFile.saveFile(MainActivity.this, bitmap);// we use the returned file from the SaveFile class
                Uri myUri = Uri.fromFile(myPictureFile);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);//need for share
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This picture is sent from the Color App that I created myself.");//specify the subject and text.
                shareIntent.putExtra(Intent.EXTRA_STREAM, myUri); // pass the file with uri
                startActivity(Intent.createChooser(shareIntent,"Let's share your picture with others!"));

            }
            catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(MainActivity.this, "OnActivityResult is called.",Toast.LENGTH_SHORT).show();
        if(requestCode == CAMERA_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {

            //for get the picture or retrieves a map of extended data from the intent
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");

            //pass the value for the picture bitmap
            colorful = new Colorful(bitmap, 0.0f, 0.0f, 0.0f);

            imgPhoto.setImageBitmap(bitmap);

        }
    }

    private class ColorizationHandler implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        //That method using when user starting interaction with the seekbar


        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //That method using when user change the progress

            if(fromUser) {

                if(seekBar == redColorSeekBar) {

                    colorful.setRedColorValue(progress / 100);
                    redColorSeekBar.setProgress( (int) (100 * (colorful.getRedColorValue())));
                    txtRedColorValue.setText(colorful.getRedColorValue() + "");

                } else if (seekBar == greenColorSeekBar) {

                    colorful.setGreenColorValue(progress / 100);
                    greenColorSeekBar.setProgress( (int) (100 * (colorful.getGreenColorValue())));
                    txtGreenColorValue.setText(colorful.getGreenColorValue() + "");

                } else if (seekBar == blueColorSeekBar) {

                    colorful.setBlueColorValue(progress / 100);
                    blueColorSeekBar.setProgress( (int) (100 * (colorful.getBlueColorValue())));
                    txtBlueColorValue.setText(colorful.getBlueColorValue() + "");

                }

                bitmap = colorful.returnTheColorizedBitmap();
                imgPhoto.setImageBitmap(bitmap);

            }

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        //That method using when user stop the interaction with the seekbar



        }
    }
}
