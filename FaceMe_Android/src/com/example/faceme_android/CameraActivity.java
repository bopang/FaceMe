package com.example.faceme_android;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.app.Activity;
import android.database.MergeCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import android.graphics.*;

public class CameraActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;
    
    private Bitmap bmPoster;
    
    private Mat mPoster;
    private Mat mResizedPoster;
    private Mat m1;
    
    int imageHeight;
    int imageWidth;
    

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public CameraActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
        
        bmPoster = Tools.getBitmapFromAsset(this.getApplicationContext(), "iron_man_3_noFace.png");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemSwitchCamera = menu.add("Toggle Native/Java camera");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String toastMesage = new String();
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);

        if (item == mItemSwitchCamera) {
            mOpenCvCameraView.setVisibility(SurfaceView.GONE);
            mIsJavaCamera = !mIsJavaCamera;

            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
            toastMesage = "Java Camera";


            mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
            mOpenCvCameraView.setCvCameraViewListener(this);
            mOpenCvCameraView.enableView();
            Toast toast = Toast.makeText(this, toastMesage, Toast.LENGTH_LONG);
            toast.show();
        }

        return true;
    }

    public void onCameraViewStarted(int width, int height) {
    	mPoster = new Mat();
    	mResizedPoster = new Mat(height, width, CvType.CV_8UC1);
    	m1 = new Mat();
    	
    	Utils.bitmapToMat(bmPoster, mPoster);
    	imageWidth = (int) mPoster.size().width;
    	imageHeight = (int) mPoster.size().height;
    	
    	if(imageHeight > imageWidth){
    		Core.transpose(mPoster, m1);
    		Core.flip(m1, mPoster, 0);
    	}
    	
    	Imgproc.resize(mPoster, mResizedPoster, mResizedPoster.size());
    	
    	
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        //m1 = MergeCameraAndPoster(inputFrame.rgba(), mResizedPoster, 0.5, 0.5);
        Core.addWeighted(inputFrame.rgba(), 0.3, mResizedPoster, 0.7, 0.0, m1);
    	
        return m1;
    }
    
    private Mat MergeCameraAndPoster(Mat mCamera, Mat mPoster, double alpha, double beta){
    	Mat result = new Mat(mCamera.size(), mCamera.type());
    	byte[] color1 = new byte[4];
    	byte[] color2 = new byte[4];
    	byte[] color3 = new byte[4];
    	for(int i=0; i<mCamera.size().width; i++){
    		for(int j=0; j<mCamera.size().height; j++){
    			mCamera.get(j, i, color1);
    			mPoster.get(j, i, color2);
    			for(int k=0; k<3; k++){
    				color3[k] = (byte) (color1[k] * alpha * color1[3] + color2[k] * beta * color2[3]);
    			}
    			result.put(j, i, color3);
    		}
    	}
    	
    	return result;
    }
}
