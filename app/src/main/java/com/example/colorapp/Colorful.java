package com.example.colorapp;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Colorful {

    private Bitmap bitmap;
    private float redColorValue;
    private float greenColorValue;
    private float blueColorValue;

    public Colorful(Bitmap newBitmap, float redValue, float greenValue, float blueValue) {

        bitmap = newBitmap;
        setRedColorValue(redValue);
        setGreenColorValue(greenValue);
        setBlueColorValue(blueValue);


    }

    public void setRedColorValue(float redValue) {

        if(redValue >= 0 && redValue <= 1) {

            redColorValue = redValue;

        }
    }

    public void setGreenColorValue(float greenValue) {
        if(greenValue >= 0 && greenValue <= 1) {

            greenColorValue = greenValue;

        }
    }

    public void setBlueColorValue(float blueValue) {
        if (blueValue >= 0 && blueValue <= 1) {

            blueColorValue = blueValue;

        }

    }

    public float getRedColorValue() {

        return redColorValue;

    }

    public float getGreenColorValue() {

        return greenColorValue;

    }

    public float getBlueColorValue() {

        return  blueColorValue;

    }
    
}