package com.example.faceme_android;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.MergeCursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.*;
import android.graphics.Bitmap.Config;

public class CameraActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;
    
    float x = 0.2875f;
    float y = 0.1226f;
    float faceWidth = 0.33f;
   
    private Bitmap bmPosterNF;
    private Bitmap bmPoster;
    
    private Mat mPosterNF;
    private Mat mPoster;
    private Mat mResizedPosterNF;
    private Mat mResizedPoster; 
    private Mat mCamera;
    private Mat m1;
    private Mat m2;
    private Mat m3;
    private Mat mask;
    private Mat ones;
    private Mat mPhoto;
    private ArrayList<Mat> allChannels1;
    private ArrayList<Mat> allChannels2;
    
    
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
        
        ImageButton takePictureButton = (ImageButton) findViewById(R.id.takePicture_Button);
        
        takePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MergeCameraPoster();
				
				Core.flip(mPhoto, m1, 0);
				Core.transpose(m1, mPhoto);
	    		
				Bitmap bm = Bitmap.createBitmap(mPhoto.width(), mPhoto.height(), Config.ARGB_8888);
				Utils.matToBitmap(mPhoto, bm);

				
				try {
				    String filename = Environment.getExternalStorageDirectory().getPath() +"/CosplayTmp.png";
					FileOutputStream out = new FileOutputStream(filename);
					bm.compress(Bitmap.CompressFormat.PNG, 90, out);
				    out.close();
				} catch (Exception e) {
				    e.printStackTrace();
				}
				
				Intent intent = new Intent(getBaseContext(), PictureViewActivity.class);
				
				startActivity(intent);
			}
		});
        
        bmPosterNF = Tools.getBitmapFromAsset(this.getApplicationContext(), "iron_man_3_noFace.png");
        bmPoster = Tools.getBitmapFromAsset(this.getApplicationContext(), "iron_man_3_Face.jpg");
        
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
    	mPosterNF = new Mat();
    	mResizedPosterNF = new Mat(height, width, CvType.CV_8UC1);
    	mPoster = new Mat();
    	mResizedPoster =  new Mat(height, width, CvType.CV_8UC1);
    	
    	m1 = new Mat();
    	m2 = new Mat();
    	m3 = new Mat();
    	mCamera = new Mat();
    	mask = new Mat();
    	mPhoto = new Mat();
    	allChannels1 = new ArrayList<Mat>(4);
    	
    	
    	Utils.bitmapToMat(bmPosterNF, mPosterNF);
    	Utils.bitmapToMat(bmPoster, mPoster);
    	
    	imageWidth = (int) mPosterNF.size().width;
    	imageHeight = (int) mPosterNF.size().height;
    	
    	if(imageHeight > imageWidth){
    		Core.transpose(mPosterNF, m1);
    		Core.flip(m1, mPosterNF, 0);
    		
    		Core.transpose(mPoster, m1);
    		Core.flip(m1, mPoster, 0);
    	}
    	
    	Imgproc.resize(mPosterNF, mResizedPosterNF, mResizedPosterNF.size());
    	Imgproc.resize(mPoster, mResizedPoster, mResizedPoster.size());
    	
    	ones = Mat.ones(mResizedPosterNF.rows(), mResizedPosterNF.cols(), CvType.CV_8UC1);
    	Core.multiply(ones, new Scalar(255), ones);
    	allChannels2 = new ArrayList<Mat>();
    	allChannels2.add(Mat.zeros(mResizedPosterNF.rows(), mResizedPosterNF.cols(), mResizedPosterNF.type()));
    	allChannels2.add(Mat.zeros(mResizedPosterNF.rows(), mResizedPosterNF.cols(), mResizedPosterNF.type()));
    	allChannels2.add(Mat.zeros(mResizedPosterNF.rows(), mResizedPosterNF.cols(), mResizedPosterNF.type()));
    	allChannels2.add(Mat.zeros(mResizedPosterNF.rows(), mResizedPosterNF.cols(), mResizedPosterNF.type()));
    	
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        //m1 = MergeCameraAndPoster(inputFrame.rgba(), mResizedPoster, 0.5, 0.5);
    	int winWidth, winHeight;
    	
    	winWidth = (int) (mResizedPoster.width() * faceWidth);
    	winHeight = (int) (mResizedPoster.height() * faceWidth);
    	int winY = (int) (mResizedPoster.width() * x);
    	int winX = (int) (mResizedPoster.height() * y);
    	
    	
    	Core.flip(inputFrame.rgba(), m1, 1);
    	m1.copyTo(m2);

    	
    	
    	//Mat win = m1.submat(dy/2, mResizedPoster.height()-dy/2,dx/2, mResizedPoster.width()-dx/2);
    	Mat win = m1.submat(winY, winY + winHeight, winX, winX + winWidth);
    	Imgproc.resize(m2, win, win.size());
    	
//      Core.addWeighted(m1, 0.5, mResizedPoster, 0.5, 0.0, m2);
    	
        mResizedPoster.copyTo(m2);
        Mat winPoster = m2.submat(winY, winY + winHeight, winX, winX + winWidth);
        Core.addWeighted(win, 0.5, winPoster, 0.5, 0.0, winPoster);
        
        
        m1.copyTo(mCamera);
    	
        return m2;
    }
    
    public void MergeCameraPoster()
    {
    	Core.split(mResizedPosterNF, allChannels1);
    	Mat alpha = allChannels1.get(3);
    	Core.absdiff(alpha, new Scalar(255.0f), mask);
    	Core.split(mCamera, allChannels1);
    	
    	Core.add(allChannels1.get(0), new Scalar(0.0f), allChannels2.get(0), mask);
    	Core.add(allChannels1.get(1), new Scalar(0.0f), allChannels2.get(1), mask);
    	Core.add(allChannels1.get(2), new Scalar(0.0f), allChannels2.get(2), mask);
    	Core.add(allChannels1.get(3), new Scalar(0.0f), allChannels2.get(3), mask);
    	
    	Core.merge(allChannels2, m2);
    	
    	Core.addWeighted(mResizedPosterNF, 1.0, m2, 1.0, 0.0, mPhoto);
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
