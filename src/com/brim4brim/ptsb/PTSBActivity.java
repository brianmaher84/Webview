package com.brim4brim.ptsb;

import com.brim4brim.ptsb.web.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class PTSBActivity extends Activity {
	
	private WebViewPtsb webview;
	
    /** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        webview= new WebViewPtsb(this);
        
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClientPtsb(this));
        webview.setWebViewClient(new WebViewClientPtsb(this));
                        
        this.setProgressBarVisibility(true);
        this.setContentView(webview);
        
        webview.loadUrl(getString(R.string.open_24_url));
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return new MainMenuSelectedHandler(this).handleMenuSelect(item, webview);
    }
}