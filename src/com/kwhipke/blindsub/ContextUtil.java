package com.kwhipke.blindsub;

import android.content.Context;

/**
 * Provides the context for the whole application in a way that makes more sense than having to call a
 * static method on BlindSubActivity
 * @author Kyle
 *
 */
public class ContextUtil {
	public static Context getAppContext() {
		return BlindSubActivity.getAppContext();
	}
}
