package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;

public class VsModeStartActivity extends AppCompatActivity{

    NumberPicker numberPicker;
    EditText etName1,etName2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vs_mode_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        numberPicker=findViewById(R.id.pickerTime);
        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(1);

        etName1 = findViewById(R.id.etName1);
        etName2 = findViewById(R.id.etName2);

        findViewById(R.id.btPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.getInstance().setPlayer1Name(etName1.getText().toString());
                Setting.getInstance().setPlayer2Name(etName2.getText().toString());
                Setting.getInstance().setPlayTime(numberPicker.getValue());
                Setting.getInstance().setPlayer1Score(0);
                Setting.getInstance().setPlayer2Score(0);

                SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
                QuestionBundle.getInstance().setLevel(Setting.getInstance().getLevel());
                QuestionBundle.getInstance().generateQuestionList(Setting.getInstance().getY(),Setting.getInstance().getX());
                Setting.getInstance().setCurrentQuestion(0);
                startActivity(new Intent(VsModeStartActivity.this, VsModePlayActivity.class));
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
