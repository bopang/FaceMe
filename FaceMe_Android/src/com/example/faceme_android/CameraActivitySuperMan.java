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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.graphics.*;
import android.graphics.Bitmap.Config;

public class CameraActivitySuperMan extends Activity implements CvCameraViewListener2, OnSeekBarChangeListener {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private SeekBar zoomBar;
    
    
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;
    
    float x = 0.28f;
    float y = 0.0f;
    float faceWidth = 0.28f;
   
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
    
    private Mat mPosterCopy;
    private Mat mPosterNFCopy;
    private Mat rWinPoster;
    private Mat rWinPosterNF;
    
    private ArrayList<Mat> allChannels1;
    private ArrayList<Mat> allChannels2;
    
    
    int imageHeight;
    int imageWidth;
    int faceWinWidth, faceWinHeight;
    int faceWinX, faceWinY;
    
    
    

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

    public CameraActivitySuperMan() {
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
       // mOpenCvCameraView.setKeepScreenOn(true);
        
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
        
        zoomBar = (SeekBar) findViewById(R.id.zoomBar);
        zoomBar.setOnSeekBarChangeListener(this);
        
        bmPosterNF = Tools.getBitmapFromAsset(this.getApplicationContext(), "superman_noFace.png");
        bmPoster = Tools.getBitmapFromAsset(this.getApplicationContext(), "superman_Face.jpg");
        
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
    	faceWinWidth = (int) (mResizedPoster.width() * faceWidth);
    	faceWinHeight = (int) (mResizedPoster.height() * faceWidth);
    	faceWinY = (int) (mResizedPoster.width() * x);
    	faceWinX = (int) (mResizedPoster.height() * y);
    	
    	mPosterCopy = new Mat();
    	mPosterNFCopy = new Mat();
    	
    	mResizedPosterNF.copyTo(mPosterNFCopy);
    	
    	
    	rWinPosterNF = mPosterNFCopy.submat(faceWinY, faceWinY + faceWinHeight, faceWinX, faceWinX + faceWinWidth);
    	
    	
    	ones = Mat.ones(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1);
    	Core.multiply(ones, new Scalar(255), ones);
    	allChannels2 = new ArrayList<Mat>();
    	allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
    	allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
    	allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
    	allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
    	
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        //m1 = MergeCameraAndPoster(inputFrame.rgba(), mResizedPoster, 0.5, 0.5);
    	
    	int zoomWidth, zoomHeight;
    	
    	
    	
    	zoomWidth = (int) (zoomBar.getProgress()/100.0f * (mResizedPoster.width() - faceWinWidth) + faceWinWidth);
    	zoomHeight = zoomWidth  * mResizedPoster.height() / mResizedPoster.width();
    	int dx = (mResizedPoster.width() - zoomWidth)/2;
    	int dy = (mResizedPoster.height() - zoomHeight)/2;
    	
    	m3 =inputFrame.rgba().submat(dy, dy + zoomHeight, dx, dx + zoomWidth);
    	
    	Core.flip(m3, m1, 1);
    	
    	m1.copyTo(m2);
    	
    	inputFrame.rgba().copyTo(m3);;
    	
    	
    	//Mat win = m1.submat(dy/2, mResizedPoster.height()-dy/2,dx/2, mResizedPoster.width()-dx/2);
    	Mat winCamera = m3.submat(faceWinY, faceWinY + faceWinHeight, faceWinX, faceWinX + faceWinWidth);
    	Imgproc.resize(m2, winCamera, winCamera.size());
    	
//      Core.addWeighted(m1, 0.5, mResizedPoster, 0.5, 0.0, m2);
    	
    	mResizedPoster.copyTo(mPosterCopy);
    	rWinPoster = mPosterCopy.submat(faceWinY, faceWinY + faceWinHeight, faceWinX, faceWinX + faceWinWidth);
        Core.addWeighted(winCamera, 0.6, rWinPoster, 0.4, 0.0, rWinPoster);
  
        winCamera.copyTo(mCamera);
    	
        return mPosterCopy;
    }
    
    public void MergeCameraPoster()
    {
    	
    	//Core.split(rWinPosterNF, allChannels1);
    	//Mat alpha = allChannels1.get(3);
    	
    	//Core.absdiff(alpha, new Scalar(255.0f), mask);
    	//Core.split(mCamera, allChannels1);
    	
    	//Core.add(alpha, new Scalar(-255.0f), mask);
    	
    	
    	//Core.multiply(allChannels1.get(0)), m1, dst);
    	
    	//Core.add(allChannels1.get(0), new Scalar(0.0f), allChannels2.get(0), mask);
    	//Core.add(allChannels1.get(1), new Scalar(0.0f), allChannels2.get(1), mask);
    	//Core.add(allChannels1.get(2), new Scalar(0.0f), allChannels2.get(2), mask);
    	//Core.add(allChannels1.get(3), new Scalar(0.0f), allChannels2.get(3), mask);
    	
    	
    	rWinPosterNF.convertTo(m1, CvType.CV_32F,1/255.0f);
    	Core.split(m1, allChannels1);

    	Mat alpha = new Mat();
    	allChannels1.get(3).copyTo(alpha);;
    	
    	Core.add(alpha, new Scalar(-1.0f), m2);
    	Core.multiply(m2, new Scalar(-1.0f), alpha);
    	
    	mCamera.convertTo(m1, CvType.CV_32F,1/255.0f);
    	Core.split(m1, allChannels1);
    	
    	Core.multiply(allChannels1.get(0), alpha, allChannels2.get(0));
    	Core.multiply(allChannels1.get(1), alpha, allChannels2.get(1));
    	Core.multiply(allChannels1.get(2), alpha, allChannels2.get(2));
    	allChannels2.set(3, alpha);

    	Core.merge(allChannels2, m2);
    	m2.convertTo(m1, CvType.CV_8UC4,255.0f);
    	//m1.copyTo(rWinPosterNF);
    	//System.out.println(alpha.dump());
    	Core.addWeighted(rWinPosterNF, 1.0, m1, 1.0, 0.0, rWinPosterNF);
    	
    	mPosterNFCopy.copyTo(mPhoto);
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
