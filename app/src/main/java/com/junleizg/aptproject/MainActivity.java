package com.junleizg.aptproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.junleizg.annotations.FBinder;
import com.junleizg.annotations.Enabler;
import com.junleizg.annotations.EnablerInjector;


public class MainActivity extends AppCompatActivity {

    @Enabler(value = "sad",group = 1)
    protected View mView1 = new TextView(this);

    @Enabler(value = "happy",group = 1)
    protected View mView2 = new TextView(this);

    @FBinder(value = "sorrow",group = 2)
    protected View mView3 = new TextView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnablerInjector.inject(this);
    }
}
