package com.newsapp.cetbusadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addStudent(View view) {
        startActivity(new Intent(MainActivity.this,AddStudentActivity.class));
    }

    public void addBus(View view) {
        startActivity(new Intent(MainActivity.this,AddBusActivity.class));
    }

    public void addQueries(View view) {
        startActivity(new Intent(MainActivity.this,AddQueriesActivity.class));
    }
}
