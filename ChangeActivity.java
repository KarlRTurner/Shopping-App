package com.example.james.group_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ChangeActivity extends AppCompatActivity {

    //Declaring buttons
    Button picBtn, enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        //Getting ID for buttons
        picBtn = (Button)findViewById(R.id.picBtn);
        enterBtn = (Button)findViewById(R.id.enterBtn);

        //Creating listener and intent for picture button

    }
}
