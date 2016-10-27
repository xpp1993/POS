package com.lxkj.administrator.pos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    static {
        System.loadLibrary("libhy_gpio_jni");
        System.loadLibrary("libhy_uart_jni");
    }
}
