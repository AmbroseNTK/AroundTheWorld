package ntk.ambrose.aroundtheworld;

public class Setting {
    private boolean isMute;
    private String country;

    private Setting(){

    }
    private static Setting instance;
    public static Setting getInstance() {
        if (instance == null)
            instance = new Setting();
        return instance;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
