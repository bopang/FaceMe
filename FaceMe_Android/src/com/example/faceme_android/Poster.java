package com.example.faceme_android;

import android.graphics.Bitmap;

public class Poster {
public Bitmap posterPic;
public String posterTitle;
public String posterDescription;
public Poster(String postertitle,Bitmap posterpic){
	posterTitle=postertitle;
	posterPic=posterpic;
}
public Bitmap getPosterPic() {
	return posterPic;
}
public void setPosterPic(Bitmap posterPic) {
	this.posterPic = posterPic;
}
public String getPosterTitle() {
	return posterTitle;
}
public void setPosterTitle(String posterTitle) {
	this.posterTitle = posterTitle;
}
public String getPosterDescription() {
	return posterDescription;
}
public void setPosterDescription(String posterDescription) {
	this.posterDescription = posterDescription;
}






}
