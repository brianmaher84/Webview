package com.brim4brim.ptsb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.net.http.SslError;
import android.widget.Toast;

public class PTSBActivity extends Activity {
	
	private WebView webview;
	
    /** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        webview= new WebView(this) {        	
        	@SuppressWarnings("unused")
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        		
        		final int sslError = error.getPrimaryError();
        		String sslErrorMessage = getString(R.string.SSL_UNKNOWN);
        		
        		switch(sslError) {
        		case SslError.SSL_EXPIRED:
        			sslErrorMessage = getString(R.string.SSL_EXPIRED);
        			break;
        		case SslError.SSL_IDMISMATCH:
        			sslErrorMessage = getString(R.string.SSL_IDMISMATCH);
        			break;
        		case SslError.SSL_NOTYETVALID:
        			sslErrorMessage = getString(R.string.SSL_NOTYETVALID);
        			break;
        		case SslError.SSL_UNTRUSTED:
        			sslErrorMessage = getString(R.string.SSL_UNTRUSTED);
        			break;
        		}
        		
        		Toast.makeText(getApplicationContext(), getString(R.string.ssl_disclaimer_error) + sslErrorMessage , Toast.LENGTH_LONG).show();
                handler.proceed(); // Ignore SSL certificate errors
            }
        };
        
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webview.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
          public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            activity.setProgress(progress * 1000);
          }
        });
        webview.setWebViewClient(new WebViewClient() {
          public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, getString(R.string.unexpect_error) + description, Toast.LENGTH_SHORT).show();
          }
        });
                
        
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
            	webview.loadUrl(getString(R.string.open_24_url));
                return true;
            case R.id.about:
            String appVersion = getString(R.string.empty_string);
			PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				appVersion = pInfo.versionName;
			} catch (NameNotFoundException e) {
				//TODO: logging 
			}
			
			DialogInterface.OnClickListener dialogAboutClickListener = new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int which) {
        	        switch (which){
        	        case DialogInterface.BUTTON_POSITIVE:
        	            dialog.cancel();
        	            break;
        	        }
        	    }
        	};        
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage(getString(R.string.about_text) + appVersion).setPositiveButton(getString(R.string.ok), dialogAboutClickListener).show();     
                return true;
            case R.id.exit:
            	DialogInterface.OnClickListener dialogYesNoClickListener = new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	            finish();
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            //No button clicked
            	            break;
            	        }
            	    }
            	};        
            	
            	AlertDialog.Builder builderYesNo = new AlertDialog.Builder(this);
            	builderYesNo.setMessage(getString(R.string.are_you_sure)).setPositiveButton(getString(R.string.yes), dialogYesNoClickListener)
            	    .setNegativeButton(getString(R.string.no), dialogYesNoClickListener).show();
            	
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}