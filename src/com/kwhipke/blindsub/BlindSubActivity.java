package com.kwhipke.blindsub;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

/**
 * Activity which simply sets the global
 * context variable on creation.
 * @author Kyle
 *
 */
public abstract class BlindSubActivity extends Activity {
	private static Context context;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        
	}
	
	public static Context getAppContext() {
		return context;
	}
}
