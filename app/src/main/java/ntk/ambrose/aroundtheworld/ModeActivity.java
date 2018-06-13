package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.games.Games;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;

public class ModeActivity extends AppCompatActivity {

    Button btSetting;
    Button btFlagMode;
    Button btNameMode;
    Button btLeaderboard;
    Button btTutorial;

    Animation showButtonAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_activity);
        btFlagMode=findViewById(R.id.btFlagMode);
        btFlagMode.setOnClickListener(view -> {
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
            QuestionBundle.getInstance().setLevel(Setting.getInstance().getLevel());
            QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(),Setting.getInstance().getX());
            Log.d("APP","Created question bundle");
            Setting.getInstance().setCurrentQuestion(0);
            startActivity(new Intent(ModeActivity.this,FlagModeActivity.class));
            finish();
        });
        btSetting = findViewById(R.id.btSetting);
        btSetting.setOnClickListener(view -> {
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
            startActivity(new Intent(ModeActivity.this,OptionActivity.class));
        });
        btNameMode=findViewById(R.id.btNameMode);
        btNameMode.setOnClickListener(view->{
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
            QuestionBundle.getInstance().setLevel(Setting.getInstance().getLevel());
            QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(),Setting.getInstance().getX());
            Log.d("APP","Created question bundle");
            Setting.getInstance().setCurrentQuestion(0);
            startActivity(new Intent(ModeActivity.this,NameModeActivity.class));
            finish();
        });

        btTutorial = findViewById(R.id.btTutorial);
        btTutorial.setOnClickListener(view->{
            startActivity(new Intent(ModeActivity.this,TutorialActivity.class));

        });

        btLeaderboard = findViewById(R.id.btLeaderBoard);
        btLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Games.Leaderboards.getLeaderboardIntent(Setting.getInstance().getApiClient(),
                        getString(R.string.leaderboard_main_leaderboard), 0);
            }
        });

        showButtonAnimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.show_button_answer);
        btFlagMode.startAnimation(showButtonAnimation);
        btNameMode.startAnimation(showButtonAnimation);
        btLeaderboard.startAnimation(showButtonAnimation);
        btSetting.startAnimation(showButtonAnimation);
        btTutorial.startAnimation(showButtonAnimation);

    }
}
