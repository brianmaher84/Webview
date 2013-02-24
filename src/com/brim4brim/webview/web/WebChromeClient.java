package com.brim4brim.webview.web;

import com.brim4brim.webview.R;

import android.app.Activity;
import android.webkit.WebView;

public class WebChromeClient extends android.webkit.WebChromeClient {

	Activity activity;
	
	public WebChromeClient(Activity activity) {
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
