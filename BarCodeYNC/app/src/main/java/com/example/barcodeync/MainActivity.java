package com.example.barcodeync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int CODIGO_INTENT = 2;
    private static final int CODIGO_PERMISOS_CAMARA = 1;
    /* access modifiers changed from: private */
    public boolean permisoCamaraConcedido = false;
    /* access modifiers changed from: private */
    public boolean permisoSolicitadoDesdeBoton = false;
    private TextView tvCodigoLeido;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        verificarYPedirPermisosDeCamara();
        Button btnEscanear = (Button) findViewById(R.id.btnEscanear);
        this.tvCodigoLeido = (TextView) findViewById(R.id.tvCodigoLeido);
        btnEscanear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!MainActivity.this.permisoCamaraConcedido) {
                    Toast.makeText(MainActivity.this, "Por favor permite que la app acceda a la cámara", 0).show();
                    MainActivity.this.permisoSolicitadoDesdeBoton = true;
                    MainActivity.this.verificarYPedirPermisosDeCamara();
                    return;
                }
                MainActivity.this.escanear();
            }
        });
    }

    /* access modifiers changed from: private */
    public void escanear() {
        startActivityForResult(new Intent(this, ActivityEscanear.class), 2);
    }

    /* access modifiers changed from: protected */
    public void request() {
        send("5156156161");
    }

    private void send(String codigo) {
        new SendService();
        SendService.execute("http://192.168.1.23:8880/bridge/barcode/set/", codigo, this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == -1 && data != null) {
            String codigo = data.getStringExtra("codigo");
            this.tvCodigoLeido.setText(codigo);
            send(codigo);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                permisoDeCamaraDenegado();
                return;
            }
            if (this.permisoSolicitadoDesdeBoton) {
                escanear();
            }
            this.permisoCamaraConcedido = true;
        }
    }

    /* access modifiers changed from: private */
    public void verificarYPedirPermisosDeCamara() {
        String str = "android.permission.CAMERA";
        if (ContextCompat.checkSelfPermission(this, str) == 0) {
            this.permisoCamaraConcedido = true;
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{str}, 1);
    }

    private void permisoDeCamaraDenegado() {
        Toast.makeText(this, "No puedes escanear si no das permiso", 0).show();
    }
}
