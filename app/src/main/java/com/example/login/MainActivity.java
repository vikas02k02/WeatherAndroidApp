package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    MyBroadcast airplaneModeChangeReceiver = new MyBroadcast();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView username= findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            if(username.getText().toString().equals("admin")&& password.getText().toString().equals("admin")){
                Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                openNewActivity();
            }else {
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
      });
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, weather.class);
        startActivity(intent);
    }

}

