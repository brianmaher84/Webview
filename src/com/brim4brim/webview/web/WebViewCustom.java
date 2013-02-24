package com.brim4brim.webview.web;

import com.brim4brim.webview.R;

import android.app.Activity;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewCustom extends android.webkit.WebView {

	Activity activity;

	public WebViewCustom(Activity activity) {
		super(activity);

		this.activity = activity;
	}

	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {

		final int sslError = error.getPrimaryError();
		String sslErrorMessage = activity.getString(R.string.SSL_UNKNOWN);

		switch (sslError) {
		case SslError.SSL_EXPIRED:
			sslErrorMessage = activity.getString(R.string.SSL_EXPIRED);
			break;
		case SslError.SSL_IDMISMATCH:
			sslErrorMessage = activity.getString(R.string.SSL_IDMISMATCH);
			break;
		case SslError.SSL_NOTYETVALID:
			sslErrorMessage = activity.getString(R.string.SSL_NOTYETVALID);
			break;
		case SslError.SSL_UNTRUSTED:
			sslErrorMessage = activity.getString(R.string.SSL_UNTRUSTED);
			break;
		}

		Toast.makeText(
				activity.getApplicationContext(),
				activity.getString(R.string.ssl_disclaimer_error)
						+ sslErrorMessage, Toast.LENGTH_LONG).show();
		handler.proceed(); // Ignore SSL certificate errors
	}

}
