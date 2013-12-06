package com.faceme_android;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.faceme_android.ApplicationData;
import com.example.faceme_android.CharacterSelectionActivity;
import com.example.faceme_android.PosterEntity;
import com.example.faceme_android.PosterSelectionActivity;
import com.example.faceme_android.R;
import com.example.faceme_android.UserProfile;

public class ProfileView {
	
	private Context mContext;
	private View mContentView;
	private LayoutInflater mLayoutInFlater;
	
	//private ListView mPosterList;
	//private ArrayAdapter<PosterEntity> mListAdapter;
	
	private ApplicationData mApplicationData;
	
	public ProfileView(Context context) {
		this.mContext = context;
		mApplicationData = (ApplicationData) context.getApplicationContext();
		
		mLayoutInFlater = LayoutInflater.from(context);
		this.mContentView = mLayoutInFlater.inflate(R.layout.multitab_subtab_profile, null);
		
		TextView tv_user_name = (TextView)mContentView.findViewById(R.id.tv_user_name);
		tv_user_name.setText(mApplicationData.mCurrentUser.username);
		ImageView iv_userFace = (ImageView)mContentView.findViewById(R.id.iv_userFace);
		iv_userFace.setImageBitmap(mApplicationData.mCurrentUser.getFaceBmp());
		TextView tv_user_gender = (TextView) mContentView.findViewById(R.id.tv_user_gender);
		tv_user_gender.setText(mApplicationData.mCurrentUser.getGender());
		TextView tv_user_school = (TextView) mContentView.findViewById(R.id.tv_user_school);
		tv_user_school.setText(mApplicationData.mCurrentUser.getSchool());
	
	}


	
	public Context getContext() {
		return mContext;
	}

	public View getContentView() {
		return mContentView;
	}

//	public ArrayAdapter getAdapter(){
//		return mListAdapter;
//	}

	
	
}
