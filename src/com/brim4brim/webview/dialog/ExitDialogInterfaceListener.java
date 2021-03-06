package com.brim4brim.webview.dialog;

import android.app.Activity;
import android.content.DialogInterface;

public class ExitDialogInterfaceListener implements DialogInterface.OnClickListener {

	Activity activity;
	
	public ExitDialogInterfaceListener(Activity activity) {
		super();
		this.activity = activity;
	}
	
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			activity.finish();
			break;

		case DialogInterface.BUTTON_NEGATIVE:
			// No button clicked
			break;
		}
	}

}
