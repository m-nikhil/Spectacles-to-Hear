package nikhil.camera_speech_int_camerathread;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nikhil on 05-05-2016.
 */

class MyFaceDetectionListener implements Camera.FaceDetectionListener {

    private Context context;

    public MyFaceDetectionListener(Context context){
        this.context = context;

    }
    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {

        if (faces.length > 0){
            if(MainActivity.speechRecognizer == false) {
                Log.d("FaceDetection","Face detected, Dude!");
                MainActivity.start();
                MainActivity.speechRecognizer = true;
                Toast.makeText(this.context, "Face detected", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d("No FaceDetection","Face not detected");
            MainActivity.stop();
            MainActivity.speechRecognizer = false;
            Toast.makeText(this.context, "Face detected over", Toast.LENGTH_SHORT).show();
        }
    }
}

