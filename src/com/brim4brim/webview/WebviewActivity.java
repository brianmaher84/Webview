package com.brim4brim.webview;

import com.brim4brim.webview.R;
import com.brim4brim.webview.web.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class WebviewActivity extends Activity {

	private WebViewCustom webview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		initWebView();
		setContentView(webview);	
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
	
		if (webview == null) {
			webview = new WebViewCustom(this);

			webview.getSettings().setJavaScriptEnabled(true);
			webview.setWebChromeClient(new WebChromeClient(this));
			webview.setWebViewClient(new WebViewClient(this));

			this.setProgressBarVisibility(true);

			webview.loadUrl(getString(R.string.app_url));
		}		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(webview);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the state of the WebView
		webview.saveState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// Restore the state of the WebView
		webview.restoreState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new MainMenuSelectedHandler(this)
				.handleMenuSelect(item, webview);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
	        webview.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
	}
}