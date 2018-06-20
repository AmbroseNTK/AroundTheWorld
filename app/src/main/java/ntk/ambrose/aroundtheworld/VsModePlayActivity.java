package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;
import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class VsModePlayActivity extends AppCompatActivity{

    ProgressBar progressScore;
    ProgressBar progressTime;

    Button btA1,  btA2,  btB1,  btB2,  btC1, btC2, btD1, btD2;

    ImageView imgFlag;

    TextView tvNamePlayer1, tvNamePlayer2, tvPlayer1Score, tvPlayer2Score, tvAnsA, tvAnsB, tvAnsC, tvAnsD;

    CountDownTimer countDownTimer;

    QuestionBundle.Question currentQuestion;

    @Override
    public void onBackPressed() {
        gameOver();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vs_mode_play_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        progressScore = findViewById(R.id.progressScore);
        progressScore.setMax(100);
        progressScore.setProgress(50);

        progressTime = findViewById(R.id.progressTime);

        imgFlag=findViewById(R.id.imgFlag);

        tvNamePlayer1 = findViewById(R.id.tvNamePlayer1);
        tvNamePlayer2 = findViewById(R.id.tvNamePlayer2);

        tvPlayer1Score = findViewById(R.id.tvPlayer1Score);
        tvPlayer2Score = findViewById(R.id.tvPlayer2Score);

        tvAnsA = findViewById(R.id.tvAnsA);
        tvAnsB = findViewById(R.id.tvAnsB);
        tvAnsC = findViewById(R.id.tvAnsC);
        tvAnsD = findViewById(R.id.tvAnsD);

        btA1 = findViewById(R.id.btA1);
        btA2 = findViewById(R.id.btA2);

        btB1 = findViewById(R.id.btB1);
        btB2 = findViewById(R.id.btB2);

        btC1 = findViewById(R.id.btC1);
        btC2 = findViewById(R.id.btC2);

        btD1 = findViewById(R.id.btD1);
        btD2 = findViewById(R.id.btD2);

        progressTime.setMax(Setting.getInstance().getPlayTime()*60);
        progressTime.setProgress(0);

        btA1.setOnClickListener(view -> checkAnswer(tvAnsA.getText().toString(),"1"));
        btA2.setOnClickListener(view -> checkAnswer(tvAnsA.getText().toString(),"2"));
        btB1.setOnClickListener(view -> checkAnswer(tvAnsB.getText().toString(),"1"));
        btB2.setOnClickListener(view -> checkAnswer(tvAnsB.getText().toString(),"2"));
        btC1.setOnClickListener(view -> checkAnswer(tvAnsC.getText().toString(),"1"));
        btC2.setOnClickListener(view -> checkAnswer(tvAnsC.getText().toString(),"2"));
        btD1.setOnClickListener(view -> checkAnswer(tvAnsD.getText().toString(),"1"));
        btD2.setOnClickListener(view -> checkAnswer(tvAnsD.getText().toString(),"2"));



        countDownTimer = new CountDownTimer(Setting.getInstance().getPlayTime()*60000,1000) {
            @Override
            public void onTick(long l) {
                progressTime.setProgress(progressTime.getProgress()+1);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
        showQuestion();
    }

    private void showQuestion(){
        currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());

        tvNamePlayer1.setText(Setting.getInstance().getPlayer1Name());
        tvNamePlayer2.setText(Setting.getInstance().getPlayer2Name());

        tvPlayer1Score.setText(String.valueOf(Setting.getInstance().getPlayer1Score()));
        tvPlayer2Score.setText(String.valueOf(Setting.getInstance().getPlayer2Score()));
        imgFlag.setImageResource(this.getResources().getIdentifier(currentQuestion.getQuestion().getCode(),"drawable",getPackageName()));
        tvAnsA.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsA()));
        tvAnsB.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsB()));
        tvAnsC.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsC()));
        tvAnsD.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsD()));

    }

    private void gameOver() {
        SoundManager.getInstance().stopAll();
        finish();
        startActivity(new Intent(VsModePlayActivity.this,GameOverActivity.class));
    }

    private void checkAnswer(String ans, String player) {

        if (ans.equals(currentQuestion.getQuestion().getName())) {
            if (player.equals("1")) {
                Setting.getInstance().setPlayer1Score(Setting.getInstance().getPlayer1Score() + 100);
            } else {
                Setting.getInstance().setPlayer2Score(Setting.getInstance().getPlayer2Score() + 100);
            }
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_CORRECT, false);
            Setting.getInstance().setCurrentQuestion(Setting.getInstance().getCurrentQuestion() + 1);
            if (Setting.getInstance().getCurrentQuestion() == QuestionBundle.getInstance().getQuestionArrayList().size()) {
                Setting.getInstance().setCurrentQuestion(0);
                QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(), Setting.getInstance().getX());
            }
            currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());
            showQuestion();
        } else {
            if (player.equals("1")) {
                Setting.getInstance().setPlayer1Score(Setting.getInstance().getPlayer1Score() - 100);
            } else {
                Setting.getInstance().setPlayer2Score(Setting.getInstance().getPlayer2Score() - 100);
            }
            //SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_WRONG, false);
            Setting.getInstance().setCurrentQuestion(Setting.getInstance().getCurrentQuestion() + 1);
            if (Setting.getInstance().getCurrentQuestion() == QuestionBundle.getInstance().getQuestionArrayList().size()) {
                Setting.getInstance().setCurrentQuestion(0);
                QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(), Setting.getInstance().getX());
            }
            currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(Setting.getInstance().getCurrentQuestion());
            showQuestion();
        }
        if(Setting.getInstance().getPlayer1Score()+Setting.getInstance().getPlayer2Score()!=0){
            progressScore.setMax(Setting.getInstance().getPlayer1Score()+Setting.getInstance().getPlayer2Score());
            progressScore.setProgress(Setting.getInstance().getPlayer1Score());
        }

    }

}
