package it.antedesk.androidvrtutorial;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AndroidVRTutorial";
    private VrPanoramaView vrPanoramaView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vrPanoramaView = (VrPanoramaView) findViewById(R.id.vr_panorama);

        loadPanoramaImage();
    }

    private void loadPanoramaImage() {
        VrPanoramaView.Options options = null;
        InputStream inputStream = null;
        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open("panorama.jpg");
            options = new VrPanoramaView.Options();
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            vrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close input stream: " + e);
            }
        } catch (IOException e) {
            Log.e(TAG, "Exception in loadPhotoSphere: " + e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        vrPanoramaView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        vrPanoramaView.shutdown();
        super.onDestroy();
    }
}