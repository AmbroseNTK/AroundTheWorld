package ntk.ambrose.aroundtheworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    Button btRetry;
    TextView tvScore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_activity);
        btRetry=findViewById(R.id.btRetry);
        tvScore=findViewById(R.id.tvScore);
        tvScore.setText("Your Score "+Setting.getInstance().getScore());
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOverActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
