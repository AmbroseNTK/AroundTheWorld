package ntk.ambrose.aroundtheworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FlagModeActivity extends AppCompatActivity{
    TextView tvQuestionNum;
    ImageView imgFlag;
    Button btAnsA;
    Button btAnsB;
    Button btAnsC;
    Button btAnsD;

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
    }
}
