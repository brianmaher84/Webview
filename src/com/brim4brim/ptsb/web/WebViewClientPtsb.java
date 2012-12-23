package com.brim4brim.ptsb.web;

import com.brim4brim.ptsb.R;

import android.app.Activity;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewClientPtsb extends android.webkit.WebViewClient {

	Activity activity;
	
	public WebViewClientPtsb(Activity activity) {
		super();
		
		this.activity = activity;
	}
	
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.unexpect_error) + description, Toast.LENGTH_SHORT).show();
    }
	
	@Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
