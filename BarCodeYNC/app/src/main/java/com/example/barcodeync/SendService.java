package com.example.barcodeync;

import android.os.Looper;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.params.HttpConnectionParams;

public class SendService {
    public static void execute(final String URL, final String codigo, final MainActivity ma) {
        new Thread() {
            public void run() {
                Looper.prepare();
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                JSONObject json = new JSONObject();
                try {
                    HttpPost post = new HttpPost(URL);
                    json.put("codigo", codigo);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType((Header) new BasicHeader("Content-Type", RequestParams.APPLICATION_JSON));
                    post.setEntity(se);
                    HttpResponse response = client.execute(post);
                    InputStream in = null;
                    if (response != null) {
                        in = response.getEntity().getContent();
                    }
                    Toast.makeText(ma, toString(in), 5).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    MainActivity mainActivity = ma;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error: ");
                    sb.append(e.toString());
                    Toast.makeText(mainActivity, sb.toString(), 5).show();
                }
                Looper.loop();
            }

            private String toString(InputStream is) throws IOException {
                char[] buffer = new char[1024];
                StringBuilder out = new StringBuilder();
                out.append("Resultado del servidor: \n");
                Reader in = new InputStreamReader(is, "UTF-8");
                while (true) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        return out.toString();
                    }
                    out.append(buffer, 0, rsz);
                }
            }
        }.start();
    }

    public static String toString(InputStream is) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, "UTF-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0) {
                return out.toString();
            }
            out.append(buffer, 0, rsz);
        }
    }
}
