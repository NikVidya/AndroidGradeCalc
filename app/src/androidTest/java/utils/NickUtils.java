package utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Nick on 16-Jan-17.
 * Some useful android functions
 */

public final class NickUtils {
    private NickUtils() {
    }
    public static void nickToast(String s, String length, Context context) {
        if (length.equals("short") || length.equals("Short") || length.equals("s") || length.equals("S")) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
        if (length.equals("long") || length.equals("Long") || length.equals("l") || length.equals("L")) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
    public static void nickToast(int s, String length, Context context) {
        if (length.equals("short") || length.equals("Short") || length.equals("s") || length.equals("S")) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
        if (length.equals("long") || length.equals("Long") || length.equals("l") || length.equals("L")) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
    public static void playSong(View v, MediaPlayer mp, int song, int toastString, Context context){
        if (mp == null){
            mp = MediaPlayer.create(context, song);
        }
        mp.start();
        nickToast(toastString, "s", context);
    }
    public static void stopSong(View v, MediaPlayer mp, int toastString, Context context){
        if (mp != null){
            mp.stop();
            nickToast(toastString, "s", context);
        }
    }
    public static void pauseSong(View v, MediaPlayer mp, int toastString, Context context){
        if (mp != null){
            mp.pause();
            nickToast(toastString, "s", context);
        }
    }
}
