package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;

public class NameModeActivity extends AppCompatActivity {

    ImageButton btAnsA;
    ImageButton btAnsB;
    ImageButton btAnsC;
    ImageButton btAnsD;
    TextView tvQuestion;
    TextView tvScore;
    QuestionBundle.Question currentQuestion;
    CountDownTimer  countDownTimer;
    ProgressBar timeProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_mode_activity);
        Setting.getInstance().setScore(0);
        SoundManager.getInstance().stopAll();
        SoundManager.getInstance().Play(SoundManager.Playlist.BG_MODE2,true);
        btAnsA=findViewById(R.id.btAnsA);
        btAnsB=findViewById(R.id.btAnsB);
        btAnsC=findViewById(R.id.btAnsC);
        btAnsD=findViewById(R.id.btAnsD);
        tvQuestion=findViewById(R.id.tvQuestion);
        tvScore=findViewById(R.id.tvScore);

        showQuestion();

        btAnsA.setOnClickListener(view->checkAnswer(currentQuestion.getAnsA()));
        btAnsB.setOnClickListener(view->checkAnswer(currentQuestion.getAnsB()));
        btAnsC.setOnClickListener(view->checkAnswer(currentQuestion.getAnsC()));
        btAnsD.setOnClickListener(view -> checkAnswer(currentQuestion.getAnsD()));

        timeProgress = findViewById(R.id.progressTime);

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
    public void gameOver(){
        countDownTimer.cancel();
        SoundManager.getInstance().Stop(SoundManager.Playlist.BG_MODE2);
        SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_WRONG,false);
        startActivity(new Intent(NameModeActivity.this,GameOverActivity.class));
        finish();
    }
    private void checkAnswer(String ans){
        if(ans.equals(currentQuestion.getQuestion().getCode())){
            countDownTimer.cancel();
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
    private void showQuestion(){
        currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());
        tvScore.setText("Score: "+Setting.getInstance().getScore());
        tvQuestion.setText(currentQuestion.getQuestion().getName());
        btAnsA.setImageResource(this.getResources().getIdentifier(currentQuestion.getAnsA(),"drawable",getPackageName()));
        btAnsB.setImageResource(this.getResources().getIdentifier(currentQuestion.getAnsB(),"drawable",getPackageName()));
        btAnsC.setImageResource(this.getResources().getIdentifier(currentQuestion.getAnsC(),"drawable",getPackageName()));
        btAnsD.setImageResource(this.getResources().getIdentifier(currentQuestion.getAnsD(),"drawable",getPackageName()));
    }
}
