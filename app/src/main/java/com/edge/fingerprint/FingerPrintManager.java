package com.edge.fingerprint;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

public class FingerPrintManager extends FingerprintManagerCompat.AuthenticationCallback {

    private Context context;
    private FingerprintManagerCompat fingerprintManagerCompat;
    private CancellationSignal cancellationSignal;
    private boolean selfCancelled;
    private FingerPrintCallback callback;

    public FingerPrintManager(Context context, FingerPrintCallback callback) {
        this.context = context;
        this.callback = callback;
        fingerprintManagerCompat = FingerprintManagerCompat.from(context);
    }


    public void startListening() {
        cancellationSignal = new CancellationSignal();
        selfCancelled = false;
        fingerprintManagerCompat.authenticate(null, 0, cancellationSignal, this, null);
    }


    public void stopListening() {
        if (cancellationSignal != null) {
            selfCancelled = true;
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!selfCancelled) {
            callback.onError();
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        callback.onError();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        callback.onAuthenticated();
    }

    @Override
    public void onAuthenticationFailed() {
        callback.onError();
    }

    public interface FingerPrintCallback {

        void onAuthenticated();

        void onError();
    }
}
