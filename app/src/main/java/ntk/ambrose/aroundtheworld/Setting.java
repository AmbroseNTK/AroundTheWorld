package ntk.ambrose.aroundtheworld;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.LeaderboardsClient;

/**
 * Lưu trữ các cài đặt ứng dụng
 */
public class Setting {
    private boolean isMute;
    private String country="vn";
    private int x=9;
    private int y=2;
    private int score;
    private int highScore;
    private int currentQuestion;
    private int level =4;
    private GoogleSignInAccount googleSignInAccount;
    private LeaderboardsClient leaderboardsClient;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        this.googleSignInAccount = googleSignInAccount;
    }

    public LeaderboardsClient getLeaderboardsClient() {
        return leaderboardsClient;
    }

    public void setLeaderboardsClient(LeaderboardsClient leaderboardsClient) {
        this.leaderboardsClient = leaderboardsClient;
    }
}
