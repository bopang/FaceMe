package com.example.faceme_android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RateAndCommentActivity extends Activity {

	List<UserComment> commentList;
	ListView userCommentsView;
	ArrayAdapter<UserComment> commentAdapter;
	EditText newCommentTextField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_and_comment);
		
		ImageView poster = (ImageView) findViewById(R.id.imageView_rating);
		userCommentsView=(ListView) findViewById(R.id.commentsList);
		newCommentTextField = (EditText) findViewById(R.id.addCommentTextField);
		
		commentList = new ArrayList<UserComment>();
		
		
		poster.setImageBitmap(Tools.getBitmapFromAsset(this, "iron_man_3_Face full.jpg"));
		
		
		
	//	commentList.add(new UserComment("Xiaofei Zeng", "Interesting! :)", Tools.getBitmapFromAsset(this, "xiaofeiFace.jpg")));
		commentList.add(new UserComment("Ziyi Jiang", "So Cool!", Tools.getBitmapFromAsset(this, "ziyiFace.jpeg")));
	
		
		LayoutParams lp = userCommentsView.getLayoutParams();
		lp.height = 170 * commentList.size();
		
		commentAdapter = new CommentAdapter(commentList);
		userCommentsView.setAdapter(commentAdapter);
	
		
		Button btn_addComment = (Button) findViewById(R.id.leaveCommentButton);
		
		btn_addComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RateAndCommentActivity.this.runOnUiThread(new Runnable(){
				
					
					@Override
					public void run() {
						Context context = getApplicationContext();
						CharSequence text = "Upload Successful!";
						int duration = Toast.LENGTH_SHORT;
		
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						// TODO Auto-generated method stub
						commentList.add(new UserComment("Xiaofei Zeng", newCommentTextField.getText().toString(), Tools.getBitmapFromAsset(context, "xiaofeiFace.jpg")));
						LayoutParams lp = userCommentsView.getLayoutParams();
						lp.height = 170 * commentList.size();
						commentAdapter.notifyDataSetChanged();
					}
				});
			}
		});
		
		
	}
	
	public void addXiaoFei(){
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rate_and_comment, menu);
		return true;
	}
	
	
	 public class CommentAdapter extends ArrayAdapter<UserComment>{
		 public List<UserComment> commentList;
		  public CommentAdapter(List<UserComment> commentList){
			  super(RateAndCommentActivity.this,R.layout.view_comment_cell, commentList);
			  this.commentList = commentList;
		  }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View tempView=convertView;
			if(tempView==null){
				tempView=getLayoutInflater().inflate(R.layout.view_comment_cell, parent, false);
			}
			UserComment comment=commentList.get(position);
		    
			ImageView imageView=(ImageView) tempView.findViewById(R.id.imageView_profilePic);
			
            imageView.setImageBitmap(RoundCorner.getRoundedCornerBitmap(comment.getBmp(), 500));
           
			TextView name=(TextView)tempView.findViewById(R.id.textView_userName);
		    name.setText(comment.getName());
		    TextView commentText=(TextView)tempView.findViewById(R.id.editText_commentText);
		    commentText.setText(comment.getComments());

		    
			return tempView;
		}
		
	  }

}
