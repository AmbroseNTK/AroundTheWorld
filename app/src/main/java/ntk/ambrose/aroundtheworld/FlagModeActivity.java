package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;
import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class FlagModeActivity extends AppCompatActivity{
    TextView tvQuestionNum;
    ImageView imgFlag;
    Button btAnsA;
    Button btAnsB;
    Button btAnsC;
    Button btAnsD;

    QuestionBundle.Question currentQuestion;
    int current;
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

        Intent intent=getIntent();
        current = intent.getIntExtra("index",0);
        currentQuestion = QuestionBundle.getInstance().getQuestionArrayList().get(current);

        imgFlag.setImageResource(this.getResources().getIdentifier(currentQuestion.getQuestion().getCode(),"drawable",getPackageName()));
        btAnsA.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsA()));
        btAnsB.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsB()));
        btAnsC.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsC()));
        btAnsD.setText(WorldMap.getInstance().codeToName(currentQuestion.getAnsD()));
    }
}
