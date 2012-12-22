package com.brim4brim.ptsb;

import com.brim4brim.ptsb.dialog.AboutDialogInterfaceListener;
import com.brim4brim.ptsb.dialog.ExitDialogInterfaceListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.MenuItem;
import android.webkit.WebView;

public class MainMenuSelectedHandler {

	Activity activity;

	public MainMenuSelectedHandler(Activity activity) {
		this.activity = activity;
	}

	public boolean handleMenuSelect(MenuItem item, WebView webview) {
		switch (item.getItemId()) {
		case R.id.home:
			webview.loadUrl(activity.getString(R.string.open_24_url));
			return true;
		case R.id.about:
			String appVersion = activity.getString(R.string.empty_string);
			PackageInfo pInfo;
			try {
				pInfo = activity.getPackageManager().getPackageInfo(
						activity.getPackageName(), 0);
				appVersion = pInfo.versionName;
			} catch (NameNotFoundException e) {
				// TODO: logging
			}

			DialogInterface.OnClickListener dialogAboutClickListener = new AboutDialogInterfaceListener();

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(
					activity.getString(R.string.about_text) + appVersion)
					.setPositiveButton(activity.getString(R.string.ok),
							dialogAboutClickListener).show();
			return true;
		case R.id.exit:
			DialogInterface.OnClickListener dialogYesNoClickListener = new ExitDialogInterfaceListener(activity);

			AlertDialog.Builder builderYesNo = new AlertDialog.Builder(activity);
			builderYesNo
					.setMessage(activity.getString(R.string.are_you_sure))
					.setPositiveButton(activity.getString(R.string.yes),
							dialogYesNoClickListener)
					.setNegativeButton(activity.getString(R.string.no),
							dialogYesNoClickListener).show();

			return true;
		default:
			return activity.onOptionsItemSelected(item);
		}

	}
}
