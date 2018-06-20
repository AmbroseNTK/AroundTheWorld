package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.games.Games;

import java.util.Timer;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;
import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class FlagModeActivity extends AppCompatActivity{
    TextView tvQuestionNum;
    ImageView imgFlag;
    Button btAnsA;
    Button btAnsB;
    Button btAnsC;
    Button btAnsD;
    ProgressBar timeProgress;
    CardView cardView;

    QuestionBundle.Question currentQuestion;
    Timer timer;
    CountDownTimer  countDownTimer;

    Animation showButtonAnswerAnim;
    Animation flyFlagAnim;
    Animation textFadeAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_mode_activity);
        tvQuestionNum=findViewById(R.id.tvQuestionNum);
        imgFlag=findViewById(R.id.imgFlag);
        btAnsA=findViewById(R.id.btAnsA);
        btAnsB=findViewById(R.id.btAnsB);
        btAnsC=findViewById(R.id.btAnsC);
        btAnsD=findViewById(R.id.btAnsD);

        cardView = findViewById(R.id.cardImg);

        SoundManager.getInstance().stopAll();
        SoundManager.getInstance().Play(SoundManager.Playlist.BG_MODE1,true);

        Setting.getInstance().setScore(0);
        timeProgress = findViewById(R.id.progressTime);

        showButtonAnswerAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.show_button_answer);
        flyFlagAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.flag_fly);
        textFadeAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.text_fade);

        showQuestion();

        btAnsA.setOnClickListener(view -> checkAnswer(btAnsA.getText().toString()));
        btAnsB.setOnClickListener(view -> checkAnswer(btAnsB.getText().toString()));
        btAnsC.setOnClickListener(view -> checkAnswer(btAnsC.getText().toString()));
        btAnsD.setOnClickListener(view -> checkAnswer(btAnsD.getText().toString()));



        setAnimForButton(showButtonAnswerAnim);



        /*
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(timeProgress.getProgress()==100){
                    gameOver();
                }
                timeProgress.setProgress(timeProgress.getProgress()+1);

            }
        }, 0,100);
        */
        countDownTimer = new CountDownTimer(1/(Setting.getInstance().getCurrentQuestion()+1)*7*1010,1/(Setting.getInstance().getCurrentQuestion()+1)*7*10) {
            @Override
            public void onTick(long l) {
                timeProgress.setProgress(timeProgress.getProgress()+1);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }
    private void setAnimForButton(Animation anim){
        btAnsA.startAnimation(anim);
        btAnsB.startAnimation(anim);
        btAnsC.startAnimation(anim);
        btAnsD.startAnimation(anim);

    }
    public void gameOver(){
        countDownTimer.cancel();
        SoundManager.getInstance().Stop(SoundManager.Playlist.BG_MODE1);
        SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_WRONG,false);
        startActivity(new Intent(FlagModeActivity.this,GameOverActivity.class));
        finish();
    }
    private void showQuestion(){
        currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());
        cardView.startAnimation(flyFlagAnim);
        tvQuestionNum.setText("Score: "+Setting.getInstance().getScore());
        imgFlag.setImageResource(this.getResources().getIdentifier(currentQuestion.getQuestion().getCode(),"drawable",getPackageName()));
        btAnsA.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsA()));
        btAnsB.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsB()));
        btAnsC.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsC()));
        btAnsD.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsD()));
        if(showButtonAnswerAnim!=null) {
            setAnimForButton(showButtonAnswerAnim);
        }
    }
    private void checkAnswer(String ans){

        if(ans.equals(currentQuestion.getQuestion().getName())){
            countDownTimer.cancel();
            if(Setting.getInstance().getCurrentQuestion() ==10) {
                if (Setting.getInstance().getGoogleSignInAccount() != null) {
                    Games.getAchievementsClient(getBaseContext(), Setting.getInstance().getGoogleSignInAccount()).unlock(getString(R.string.achievement_first_10_flag));
                }
            }
            tvQuestionNum.startAnimation(textFadeAnim);
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_CORRECT,false);
            Setting.getInstance().setScore(Setting.getInstance().getScore()+100);
            Setting.getInstance().setCurrentQuestion(Setting.getInstance().getCurrentQuestion()+1);
            if(Setting.getInstance().getCurrentQuestion()==QuestionBundle.getInstance().getQuestionArrayList().size()){
                Setting.getInstance().setCurrentQuestion(0);
                QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(),Setting.getInstance().getX());
            }
            currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());
            showQuestion();
            timeProgress.setProgress(0);
            countDownTimer.start();
        }
        else{
            gameOver();
        }
    }
}
