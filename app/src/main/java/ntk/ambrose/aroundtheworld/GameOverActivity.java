package ntk.ambrose.aroundtheworld;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;

public class GameOverActivity extends AppCompatActivity {
    Button btRetry;
    TextView tvScore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_activity);
        btRetry=findViewById(R.id.btRetry);
        tvScore=findViewById(R.id.tvScore);
        loadHighScore();
        if(Setting.getInstance().getScore()>Setting.getInstance().getHighScore()) {
            tvScore.setText("Your New High Score " + Setting.getInstance().getScore());
            saveHighScore();
        }
        else{
            tvScore.setText("Your Score " + Setting.getInstance().getScore());
        }
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOverActivity.this,MainActivity.class));
                finish();
            }
        });
    }
    public void loadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            Setting.getInstance().setHighScore(sharedPreferences.getInt("highScore",0));
        }
    }
    public void saveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(Setting.getInstance().getScore()>Setting.getInstance().getHighScore()) {
            editor.putInt("highScore", Setting.getInstance().getScore());
            Setting.getInstance().setHighScore(Setting.getInstance().getScore());
        }
        editor.apply();
    }
    public void saveToLeaderboard(){


    }
}
