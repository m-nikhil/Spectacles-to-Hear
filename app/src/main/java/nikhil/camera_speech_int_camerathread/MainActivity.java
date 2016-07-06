// Author : Nikhil
package nikhil.camera_speech_int_camerathread;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;



public class MainActivity extends Activity
{

    private static TextView mText;
    private static SpeechRecognizer sr;
    private static final String TAG = "MyStt3Activity";
    private static Intent intent;

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;
    private FrameLayout cameraPreviewLayout;
    private ImageView capturedImageHolder;


    public static boolean speechRecognizer = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.textView1);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

       cameraPreviewLayout = (FrameLayout)findViewById(R.id.camera_preview);

        mText = (TextView) findViewById(R.id.textView1);

        CameraOpenTask camerathread = new CameraOpenTask();
        camerathread.execute();

    }

    private class CameraOpenTask extends AsyncTask<Void, Void, Camera>{
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Camera doInBackground(Void... Camera){
            return checkDeviceCamera();
        }

        @Override
        protected void onPostExecute(Camera openedcamera){
               camera = openedcamera;
               camera.setFaceDetectionListener(new MyFaceDetectionListener(MainActivity.this));
               mImageSurfaceView = new ImageSurfaceView(MainActivity.this, openedcamera);
               cameraPreviewLayout.addView(mImageSurfaceView);
        }


    }

    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }



    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {}
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");

        }
        public void onError(int error)
        {
            Log.d(TAG, "error " + error);

            sr.cancel();
            sr.startListening(intent);

        }
        public void onResults(Bundle results)
        {
            sr.startListening(intent);
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            mText.setText(data.get(0));

        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    public static void start()
    {
        sr.startListening(intent);
        mText.setText("");

        Log.i("111111","11111111");
    }

    public static void stop()
    {
        sr.cancel();
    }


}
