package com.faceme_android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.faceme_android.ApplicationData;
import com.example.faceme_android.R;

public class MultiTabActivity extends Activity {

	public static MultiTabActivity instance = null;
	private ViewPager mTabPager;
	private ImageView mTab1, mTab2, mTab3, mTab4, mTab5;
	private ImageView mTabImg;
	private int zero = 0;
	private int one, two, three, four;// ����������������
	private int currIndex;
	private ApplicationData mApplicationData;
	
	//View of each tab;
	private PosterSelectionView mPosterSelectionView;
	private NewsFeedView mNewsFeedView;
	private RankingView mRankingView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplicationData = (ApplicationData) getApplicationContext();
		//mApplicationData.loadUserInfo();
		setContentView(R.layout.multitab_bed);
		instance = this;

		mTabPager = (ViewPager) findViewById(R.id.multitabbed);

		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mTab1 = (ImageView) findViewById(R.id.play);
		mTab2 = (ImageView) findViewById(R.id.newfeeds);
		mTab3 = (ImageView) findViewById(R.id.ranking);
		mTab4 = (ImageView) findViewById(R.id.notification);
		mTab5 = (ImageView) findViewById(R.id.profile);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		mTab5.setOnClickListener(new MyOnClickListener(4));

		Display currDisplay = getWindowManager().getDefaultDisplay();// ������������������
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 5; // ��������������������
		two = one * 2;
		three = one * 3;
		four = one * 4;
		
		mPosterSelectionView = new PosterSelectionView(this);
		mNewsFeedView = new NewsFeedView(this);
		mRankingView = new RankingView(this);
		
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.multitab_subtab_play, null);
		//View view2 = mLi.inflate(R.layout.multitab_subtab_b, null);
		//View view3 = mLi.inflate(R.layout.multitab_subtab_c, null);
		View view4 = mLi.inflate(R.layout.multitab_subtab_d, null);
		View view5 = mLi.inflate(R.layout.multitab_subtab_profile, null);

		// ����������view����
		final ArrayList<View> views = new ArrayList<View>();
		//views.add(view1);
		views.add(mPosterSelectionView.getContentView());
		views.add(mNewsFeedView.getContentView());
		//views.add(view2);
		views.add(mRankingView.getContentView());
		views.add(view4);
		views.add(view5);

		// ����ViewPager������������
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				if(position == 0 && mApplicationData.mPosters.size()==0){
					mApplicationData.loadPostersData(mPosterSelectionView.getAdapter());
				}
				if(position == 1 && mApplicationData.mNews.size() == 0){
					mApplicationData.loadNewsFeed(mNewsFeedView.getAdapter());
				}
				if(position == 2){
					mRankingView.resetAdapter();
				}
				
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setCurrentItem(1);
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = null;
			switch (arg0) {
			case 0:
				/*
				 * mTab1.setImageDrawable(getResources().getDrawable(
				 * R.drawable.XXXX));
				 */
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					/*
					 * mTab2.setImageDrawable(getResources().getDrawable(
					 * R.drawable.tab_key_unpressed));
					 */
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					/*
					 * mTab3.setImageDrawable(getResources().getDrawable(
					 * R.drawable.tab_find_frd_normal));
					 */
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					/*
					 * mTab4.setImageDrawable(getResources().getDrawable(
					 * R.drawable.tab_settings_normal));
					 */
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, 0, 0, 0);
				}
				break;
			case 1:
				/*mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_key_pressed));*/
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					/*mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_house_unpressed));*/
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				/*	mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));*/
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
				/*	mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));*/
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, one, 0, 0);
				}
				break;
			case 2:
			/*	mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_pressed));*/
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
	/*				mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_house_unpressed));*/
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					/*mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_key_unpressed));*/
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					/*mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));*/
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, two, 0, 0);
				}
				break;
			case 3:
				/*mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));*/
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					/*mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_house_unpressed));*/
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					/*mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_key_unpressed));*/
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					/*mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));*/
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, three, 0, 0);
				}
				break;
			case 4:
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, four, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, four, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, four, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, four, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:��������������������
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}

	}

}
