package ntk.ambrose.aroundtheworld;

public class Setting {
    private boolean isMute;
    private String country;
    private int x;
    private int y;


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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
