package com.kwhipke.blindsub.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import org.pielot.openal.OpenAlBridge;

import com.kwhipke.blindsub.ContextUtil;

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
    public static long getSoundDuration(String wavFileName) throws IOException{
    	File file = new File(OpenAlBridge.getWavPath(wavFileName));
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
}
