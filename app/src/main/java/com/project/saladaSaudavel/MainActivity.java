package com.project.saladaSaudavel;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent loginIt = new Intent(this, LoginActivity.class);
        Intent homeIt = new Intent(this, HomeActivity.class);

        if(getSharedPreferences("LoginInfo", 0).getBoolean("Logado", false) == true){
            startActivity(homeIt);
            finish();
        }else {
            startActivity(loginIt);
            finish();
        }
    }
}
