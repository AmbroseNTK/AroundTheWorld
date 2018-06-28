package ntk.ambrose.aroundtheworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class TutorialActivity extends AppCompatActivity{

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutorial);

        findViewById(R.id.btDoneTut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView = findViewById(R.id.webTut);
        webView.loadUrl("https://youtu.be/LtcHayjS-T8");
    }
}
