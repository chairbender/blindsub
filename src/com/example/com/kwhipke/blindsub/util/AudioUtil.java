package com.example.com.kwhipke.blindsub.util;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Some helpful methods for dealing with audio
 * @author Kyle
 *
 */
public class AudioUtil {

    public static long getSoundDuration(Context context, int rawId){
	   MediaPlayer player = MediaPlayer.create(context, rawId);
	   int duration = player.getDuration();
	   player.release();
	   return duration;
	}
}
