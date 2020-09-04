package com.example.barcodeync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler;

public class ActivityEscanear extends AppCompatActivity implements ResultHandler {
    private ZXingScannerView escanerZXing;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        ZXingScannerView zXingScannerView = new ZXingScannerView(this);
        this.escanerZXing = zXingScannerView;
        setContentView((View) zXingScannerView);
    }

    public void onResume() {
        super.onResume();
        this.escanerZXing.setResultHandler(this);
        this.escanerZXing.startCamera();
    }

    public void onPause() {
        super.onPause();
        this.escanerZXing.stopCamera();
    }

    public void handleResult(Result resultado) {
        String codigo = resultado.getText();
        Intent intentRegreso = new Intent();
        intentRegreso.putExtra("codigo", codigo);
        setResult(-1, intentRegreso);
        finish();
    }
}
