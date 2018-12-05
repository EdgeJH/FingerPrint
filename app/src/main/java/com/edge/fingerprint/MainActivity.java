package com.edge.fingerprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity implements FingerPrintManager.FingerPrintCallback {

    FingerPrintManager fingerPrintManager;
    LottieAnimationView fpAnimationView;
    TextView fpGuideTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpAnimationView = findViewById(R.id.fp_animation_view);
        fpGuideTxt = findViewById(R.id.fp_guide_txt);
        fingerPrintManager = new FingerPrintManager(this,this);
        fingerPrintManager.startListening();
    }

    @Override
    public void onAuthenticated() {
        fpAnimationView.playAnimation();
        fpGuideTxt.setText("인증에 성공했습니다.");
    }

    @Override
    public void onError() {
        fpGuideTxt.setText("인증에 실패했습니다.");
    }
}
