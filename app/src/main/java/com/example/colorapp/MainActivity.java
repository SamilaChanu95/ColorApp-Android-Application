package com.example.colorapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTakePicture;
    private Button btnSavePicture;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnSavePicture = (Button) findViewById(R.id.btnSavePicture);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        redColorSeekBar = (SeekBar) findViewById(R.id.redColorSeekBar);
        greenColorSeekBar = (SeekBar) findViewById(R.id.greenColorSeekBar);
        blueColorSeekBar = (SeekBar) findViewById(R.id.blueColorSeekBar);
        txtRedColorValue = (TextView) findViewById(R.id.txtRedColorValue);
        txtGreenColorValue = (TextView) findViewById(R.id.txtGreenColorValue);
        txtBlueColorValue = (TextView) findViewById(R.id.txtBlueColorValue);

        btnTakePicture.setOnClickListener(MainActivity.this);
        btnSavePicture.setOnClickListener(MainActivity.this);
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


        }
    }
    
}
