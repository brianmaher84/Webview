package com.brim4brim.ptsb;

import com.brim4brim.ptsb.web.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class PTSBActivity extends Activity {

	private WebViewPtsb webview;

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
			webview = new WebViewPtsb(this);

			webview.getSettings().setJavaScriptEnabled(true);
			webview.setWebChromeClient(new WebChromeClientPtsb(this));
			webview.setWebViewClient(new WebViewClientPtsb(this));

			this.setProgressBarVisibility(true);

			webview.loadUrl(getString(R.string.open_24_url));
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
}