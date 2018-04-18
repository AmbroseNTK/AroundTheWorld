package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;

public class ModeActivity extends AppCompatActivity {
    Button btFlagMode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_activity);
        btFlagMode=findViewById(R.id.btFlagMode);
        btFlagMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionBundle.getInstance().setLevel(QuestionBundle.LEVEL_EASY);
                QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(),Setting.getInstance().getX());
                Log.d("APP","Created question bundle");
                Setting.getInstance().setCurrentQuestion(0);
                startActivity(new Intent(ModeActivity.this,FlagModeActivity.class));
                finish();
            }
        });
    }
}
