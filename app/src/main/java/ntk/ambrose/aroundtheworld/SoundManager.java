package ntk.ambrose.aroundtheworld;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance;

    private boolean mute;

    private String playingList;

    private SoundManager(){
        playingList="";
    }

    public static SoundManager getInstance() {
        if (instance == null)
            instance = new SoundManager();
        return instance;
    }

    public void init(Context context){
        soundList = new HashMap<>();
        //Init whole soundtrack
        soundList.put(Playlist.BG_MAIN,MediaPlayer.create(context,R.raw.bg_main));
        soundList.put(Playlist.BG_MODE1,MediaPlayer.create(context,R.raw.bg_mode1));
        soundList.put(Playlist.BG_MODE2,MediaPlayer.create(context,R.raw.bg_mode2));
        soundList.put(Playlist.SOUND_BUTTON,MediaPlayer.create(context,R.raw.sound_button));
        soundList.put(Playlist.SOUND_CORRECT,MediaPlayer.create(context,R.raw.sound_correct));
        soundList.put(Playlist.SOUND_WRONG,MediaPlayer.create(context,R.raw.sound_wrong));
        soundList.put(Playlist.SOUND_MUTE,MediaPlayer.create(context,R.raw.sound_mute));
    }

    private HashMap<String,MediaPlayer> soundList;

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void Play(String nameInPlaylist, boolean isLoop) {
               /* try {
            if (nameInPlaylist.substring(0, 1).toLowerCase().equals("bg")) {
                playingList = nameInPlaylist;
                soundList.get(playingList).stop();
            }
        }catch(Exception ex){}*/
        if (!mute) {
            try {
                if (isLoop) {
                    soundList.get(nameInPlaylist).setLooping(isLoop);
                }

                soundList.get(nameInPlaylist).start();
            } catch (Exception ex) {
                Log.e("AroundTheWorld", "Cannot play this - " + ex.getMessage());
            }
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

    public void setMuteAll(){
        mute=true;
        for (MediaPlayer player: soundList.values()) {
            player.stop();
        }
    }

    public void stopAll(){
        for (MediaPlayer player: soundList.values()) {
            if(player!=null && player.isPlaying()) {
                player.stop();
            }
        }
    }

    public static class Playlist{
        public final static String SOUND_CORRECT = "sound_correct";
        public final static String SOUND_WRONG ="sound_wrong";
        public final static String SOUND_BUTTON ="sound_button";
        public final static String BG_MAIN ="bg_main";
        public final static String BG_MODE1 = "bg_mode1";
        public final static String BG_MODE2 ="bg_mode2";
        public final static String SOUND_MUTE="sound_mute";
    }
}
