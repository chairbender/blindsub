package com.kwhipke.blindsub.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import org.pielot.openal.OpenAlBridge;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * Some helpful methods for dealing with audio
 * @author Kyle
 *
 */
public class AudioUtil {
	
	//Speed that sound travels at in the water in this game
	public static final double SOUND_METERS_PER_SECOND = 30;

	/**
	 * Get the length of the resource in millis. Will not work if OpenAl is initialized
	 * @param context
	 * @param wavFileName name of the wav file without the .wav extension
	 * @return
	 * @throws IOException 
	 */
    public static long getSoundDuration(Activity activity, String wavFileName) throws IOException{
    	File file = new File(OpenAlBridge.getWavPath(activity,wavFileName));
		MediaPlayer mp = new MediaPlayer();
		FileInputStream fs;
		FileDescriptor fd;
		fs = new FileInputStream(file);
		fd = fs.getFD();
		mp.setDataSource(fd);
		mp.prepare(); // might be optional
		int length = mp.getDuration();
	   return length;
	}
    
    /**
     * Given a heading in degrees with 0 degrees being east and 90 north,
     * convert to the openAl orientation in degrees
     * @param subgameOrientation
     * @return
     */
    public static double getOpenAlOrientation(double subgameOrientation) {
    	double sourceHeading = subgameOrientation - 180;
		if (sourceHeading < 0) {
			sourceHeading = sourceHeading + 360;
		}
		return sourceHeading;
    }
}
