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
import com.example.faceme_android.R;
import com.example.faceme_android.UserProfile;

public class RankingView {
	private Context mContext;
	private View mContentView;
	private LayoutInflater mLayoutInFlater;
	
	private ListView mUserList;
	private ArrayAdapter<UserProfile> mListAdapter;
	
	private ApplicationData mApplicationData;
	private View mLoadingView;
	
	public RankingView(Context context) {
		this.mContext = context;
		mApplicationData = (ApplicationData) context.getApplicationContext();
		
		
		mLayoutInFlater = LayoutInflater.from(context);
		this.mContentView = mLayoutInFlater.inflate(R.layout.multitab_subtab_ranking, null);
		this.mLoadingView = mContentView.findViewById(R.id.loadingView);
		
		mUserList = (ListView) mContentView.findViewById(R.id.listView_ranklist);
		mListAdapter = new UserAdapter(mApplicationData.mRankingData);
		mUserList.setAdapter(mListAdapter);
		mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int postion,
					long id) {
				// TODO Auto-generated method stub
				//PosterEntity currentPoster=mApplicationData.mPosters.get(postion);
				//mApplicationData.currentPoster = currentPoster;

				Intent intent;
				//if(postion ==0)
				//intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivityXiaofei.class);
				//else
				//intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivitySuperMan.class);
				intent =new Intent(mContext, CharacterSelectionActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		//if(mApplicationData.mPosters.size() != 0){
			hideLoadingView();
		//}
	}

	public void resetAdapter(){
		mListAdapter = new UserAdapter(mApplicationData.mRankingData);
		mUserList.setAdapter(mListAdapter);
	}
	
	public void hideLoadingView(){
		mLoadingView.setVisibility(View.GONE);
	}
	
	public Context getContext() {
		return mContext;
	}

	public View getContentView() {
		return mContentView;
	}

	public ArrayAdapter getAdapter(){
		return mListAdapter;
	}

	
	public class UserAdapter extends ArrayAdapter<UserProfile>{
		
		public UserAdapter(List<UserProfile> usersList){
			super(mContext, R.layout.cellitem_raking, usersList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View templateView;
			templateView = convertView;
			if(templateView==null){
				templateView=mLayoutInFlater.inflate(R.layout.cellitem_raking, parent, false);
			}
			
			
			UserProfile user = mApplicationData.mRankingData.get(position);
			
			TextView nameText = (TextView)templateView.findViewById(R.id.label_ranking_name);
			TextView scoreText = (TextView)templateView.findViewById(R.id.label_ranking_scoreNum);
			TextView rankText = (TextView)templateView.findViewById(R.id.label_ranking_position);
			
			nameText.setText(user.getUsername());
			scoreText.setText("1000");
			rankText.setText(""+(position+1));
			
			ImageView userPhotoImageView = (ImageView)templateView.findViewById(R.id.imageView_ranking_userPhoto);
			
			if(user.faceBmp != null)
				userPhotoImageView.setImageBitmap(user.faceBmp);
			return templateView;
		}
		
		@Override
		public void notifyDataSetChanged(){
			hideLoadingView();
			super.notifyDataSetChanged();
		}
	}
}
