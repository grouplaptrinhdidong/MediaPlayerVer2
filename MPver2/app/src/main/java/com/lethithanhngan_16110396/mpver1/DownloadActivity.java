package com.lethithanhngan_16110396.mpver1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class DownloadActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Button btnBaiHat=(Button) findViewById(R.id.btnBaiHat);
        btnBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, ListBaiHat.class);
                startActivity(intent);
            }
        });

        Button btnCaNhan=(Button) findViewById(R.id.btnCanhan);
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        searchView=(SearchView) findViewById(R.id.searchView);
    }

    // Phương thức được gọi khi người dùng click vào Button "Download".

    private boolean checkInternetConnection() {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    //test link download nhạc

    public void findClick(View view){
        String a= String.valueOf(searchView.getQuery());
        String url="https://trangtainhac.com/nhac-hay/tim-kiem/bai-hat.html?q=";
        String urlSearch=url+a;
        boolean networkOK = this.checkInternetConnection();
        if (networkOK) {
            // Một Intent không tường minh, yêu cầu mở một URL.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlSearch));
            this.startActivity(intent);
        }
    }
}
