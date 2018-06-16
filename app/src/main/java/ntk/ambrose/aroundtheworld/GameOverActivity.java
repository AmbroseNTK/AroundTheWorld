package ntk.ambrose.aroundtheworld;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.games.Games;
import com.sdsmdg.tastytoast.TastyToast;

public class GameOverActivity extends AppCompatActivity {
    Button btRetry;
    TextView tvScore;
    ShareButton shareButton;

    SharePhotoContent content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_activity);
        btRetry=findViewById(R.id.btRetry);
        tvScore=findViewById(R.id.tvScore);
        loadHighScore();
        if(Setting.getInstance().getScore()>Setting.getInstance().getHighScore()) {
            tvScore.setText("Your New High Score " + Setting.getInstance().getScore());
            saveHighScore();
            saveToLeaderboard();

        }
        else{
            tvScore.setText("Your Score " + Setting.getInstance().getScore());
        }
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
                startActivity(new Intent(GameOverActivity.this,MainActivity.class));
                finish();
            }
        });

        shareButton = findViewById(R.id.btShareFB);
        createShareOnFacebook();
        shareButton.setShareContent(content);


    }
    public void loadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            Setting.getInstance().setHighScore(sharedPreferences.getInt("highScore",0));
        }
    }
    public void saveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(Setting.getInstance().getScore()>Setting.getInstance().getHighScore()) {
            editor.putInt("highScore", Setting.getInstance().getScore());
            Setting.getInstance().setHighScore(Setting.getInstance().getScore());
        }
        editor.apply();
    }
    public void saveToLeaderboard(){
        if(Setting.getInstance().getLeaderboardsClient()!=null){
            Setting.getInstance().getLeaderboardsClient().submitScore(getString(R.string.leaderboard_main_leaderboard),Setting.getInstance().getScore());

            TastyToast.makeText(this,"High score was submitted to world Leaderboard",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        }
        else{
            TastyToast.makeText(this,"Sorry, I cannot submit your new high score",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
        }
    }

    public void createShareOnFacebook(){
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.panel);
        Bitmap mutableBitmap = image.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setTextSize(10);
        paint.setColor(Color.YELLOW);
        canvas.drawText("Score: "+Setting.getInstance().getScore(),0,0,paint);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(mutableBitmap)
                .build();
        content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

    }
}
