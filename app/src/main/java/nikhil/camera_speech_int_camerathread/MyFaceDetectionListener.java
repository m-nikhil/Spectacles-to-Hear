package nikhil.camera_speech_int_camerathread;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by Nikhil on 05-05-2016.
 */

class MyFaceDetectionListener implements Camera.FaceDetectionListener {

    public MyFaceDetectionListener(){


    }
    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length > 0){
            if(MainActivity.speechRecognizer == false) {
                Log.d("FaceDetection","Face detected, Dude!");
                MainActivity.start();
                MainActivity.speechRecognizer = true;
            }
        }
        else{
            Log.d("No FaceDetection","Face not detected");
            MainActivity.stop();
            MainActivity.speechRecognizer = false;
        }
    }
}

