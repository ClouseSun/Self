package com.abition.self;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abition.self.uielement.RoundRectDrawable;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerText = (TextView) findViewById(R.id.tv_login);
        registerText.setBackground(new RoundRectDrawable(0xffffffff));
    }
}
