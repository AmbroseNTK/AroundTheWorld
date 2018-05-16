package ntk.ambrose.aroundtheworld;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance;

    private boolean mute;

    private SoundManager(){
    }

    public static SoundManager getInstance() {
        if (instance == null)
            instance = new SoundManager();
        return instance;
    }

    public void init(Context context){
        soundList = new HashMap<>();

        //Init whole soundtrack
    }

    private HashMap<String,MediaPlayer> soundList;

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void Play(String nameInPlaylist, boolean isLoop){
        try {
            if(isLoop){
                soundList.get(nameInPlaylist).setLooping(isLoop);
            }
            soundList.get(nameInPlaylist).start();
        }
        catch(Exception ex){
            Log.e("AroundTheWorld","Cannot play this - "+ex.getMessage());
        }
    }

    public void Stop(String nameInPlaylist){
        try {
            soundList.get(nameInPlaylist).stop();
        }
        catch(Exception ex){
            Log.e("AroundTheWorld","Cannot play this - "+ex.getMessage());
        }
    }

    public static class Playlist{
        public final static String SOUND_CORRECT = "sound_correct";
        public final static String SOUND_WRONG ="sound_wrong";
        public final static String SOUND_BUTTON ="sound_button";
        public final static String BG_MAIN ="bg_main";
        public final static String BG_PLAYING = "bg_playing";
    }
}
