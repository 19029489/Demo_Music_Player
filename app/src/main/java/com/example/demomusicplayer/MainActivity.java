package com.example.demomusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);

        if (checkPermission() == true){

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // starting the service
                    startService(new Intent(MainActivity.this, MyService.class));
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // stopping the service
                    stopService(new Intent(MainActivity.this, MyService.class));
                }
            });


        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            return;
        }

    }

    private boolean checkPermission(){
        int permissionCheck_Write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                //If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}