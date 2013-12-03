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

public class PosterSelectionView {
	
	private Context mContext;
	private View mContentView;
	private LayoutInflater mLayoutInFlater;
	
	private ListView mPosterList;
	private ArrayAdapter<PosterEntity> mListAdapter;
	
	private ApplicationData mApplicationData;
	private View mLoadingView;
	
	public PosterSelectionView(Context context) {
		this.mContext = context;
		mApplicationData = (ApplicationData) context.getApplicationContext();
		
		
		mLayoutInFlater = LayoutInflater.from(context);
		this.mContentView = mLayoutInFlater.inflate(R.layout.multitab_subtab_play, null);
		this.mLoadingView = mContentView.findViewById(R.id.LoadingView);
		
		mPosterList = (ListView) mContentView.findViewById(R.id.listView_posterlist);
		mListAdapter = new posterAdapter(mApplicationData.mPosters);
		mPosterList.setAdapter(mListAdapter);
		mPosterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int postion,
					long id) {
				// TODO Auto-generated method stub
				PosterEntity currentPoster=mApplicationData.mPosters.get(postion);
				mApplicationData.currentPoster = currentPoster;
				mApplicationData.playWithNews = false;
				Intent intent;
				//if(postion ==0)
				//intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivityXiaofei.class);
				//else
				//intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivitySuperMan.class);
				intent =new Intent(mContext, CharacterSelectionActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		if(mApplicationData.mPosters.size() != 0){
			hideLoadingView();
		}
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

	
	public class posterAdapter extends ArrayAdapter<PosterEntity>{
		
		public posterAdapter(List<PosterEntity> posters){
			super(mContext, R.layout.cellitem_poster, posters);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View templateView;
			templateView = convertView;
			if(templateView==null){
				templateView=mLayoutInFlater.inflate(R.layout.cellitem_poster, parent, false);
			}
			PosterEntity currentPoster = mApplicationData.mPosters.get(position);
			TextView title = (TextView)templateView.findViewById(R.id.textView_posterTitle);
			TextView description=(TextView)templateView.findViewById(R.id.textView_posterDescription);
			title.setText(currentPoster.getMovieName());
			description.setText(currentPoster.getPosterName());
			ImageView posterImg=(ImageView)templateView.findViewById(R.id.imageView_poster);
			
			if(currentPoster.thumbnail != null)
				posterImg.setImageBitmap(currentPoster.thumbnail);
			return templateView;
		}
		
		@Override
		public void notifyDataSetChanged(){
			hideLoadingView();
			super.notifyDataSetChanged();
		}
	}
}
