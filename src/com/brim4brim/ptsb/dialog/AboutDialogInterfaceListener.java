package com.brim4brim.ptsb.dialog;

import android.content.DialogInterface;

public class AboutDialogInterfaceListener implements DialogInterface.OnClickListener {

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			dialog.cancel();
			break;
		}		
	}


}
