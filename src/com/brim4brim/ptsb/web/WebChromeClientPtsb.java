package com.brim4brim.ptsb.web;

import com.brim4brim.ptsb.R;

import android.app.Activity;
import android.webkit.WebView;

public class WebChromeClientPtsb extends android.webkit.WebChromeClient {

	Activity activity;
	
	public WebChromeClientPtsb(Activity activity) {
		super();
		
		this.activity = activity;
	}
	
	public void onProgressChanged(WebView view, int progress) {        
        activity.setTitle(activity.getString(R.string.loading));
        activity.setProgress(progress * 100);
           if(progress == 100)
              activity.setTitle(activity.getString(R.string.app_name));
        
    }
}
