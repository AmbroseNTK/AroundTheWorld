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
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sdsmdg.tastytoast.TastyToast;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;

public class ModeActivity extends AppCompatActivity {

    Button btSetting;
    Button btFlagMode;
    Button btNameMode;
    Button btLeaderboard;
    Button btTutorial;
    Button btAchievement;
    Button btPlayVsPlay;

    TextView tvLabel1;

    Animation showButtonAnimation;
    Animation labelRunAnimation;

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
                if(Setting.getInstance().getLeaderboardsClient()!=null){
                    showLeaderboard();
                }
                else{
                    TastyToast.makeText(getBaseContext(),"I cannot show world leader board, Please sign in Google first !!!",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                }
            }
        });

        btAchievement = findViewById(R.id.btAchievement);
        btAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Setting.getInstance().getLeaderboardsClient()!=null){
                    showAchievements();
                }
                else{
                    TastyToast.makeText(getBaseContext(),"I cannot show world leader board, Please sign in Google first !!!",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                }
            }
        });

        btPlayVsPlay = findViewById(R.id.btPlayVsPlay);
        btPlayVsPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ModeActivity.this,VsModeStartActivity.class));
            }
        });

        showButtonAnimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.show_button_answer);
        btFlagMode.startAnimation(showButtonAnimation);
        btNameMode.startAnimation(showButtonAnimation);
        btLeaderboard.startAnimation(showButtonAnimation);
        btAchievement.startAnimation(showButtonAnimation);
        btSetting.startAnimation(showButtonAnimation);
        btTutorial.startAnimation(showButtonAnimation);
        btPlayVsPlay.startAnimation(showButtonAnimation);

        tvLabel1 = findViewById(R.id.tvLabel1);
        labelRunAnimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.label_anim);
        labelRunAnimation.setRepeatCount(Animation.INFINITE);
        labelRunAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvLabel1.setText("SELECT MODE");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvLabel1.startAnimation(animation);
                tvLabel1.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tvLabel1.startAnimation(labelRunAnimation);
        labelRunAnimation.start();

    }
    private static final int RC_LEADERBOARD_UI = 9004;

    private void showLeaderboard() {
        Setting.getInstance().getLeaderboardsClient()
                .getLeaderboardIntent(getString(R.string.leaderboard_main_leaderboard))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }
    private static final int RC_ACHIEVEMENT_UI = 9003;

    private void showAchievements() {
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }
}
