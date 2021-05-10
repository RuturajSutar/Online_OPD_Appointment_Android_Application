package com.example.onlineopdappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginsBoth extends AppCompatActivity {


    Button hospitalLogin;
    Button patientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins_both);


        hospitalLogin=(Button)findViewById(R.id.button1);
        patientLogin=(Button)findViewById(R.id.button2);


        hospitalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginsBoth.this,HospitalLoginPage.class);
                startActivity(intent);
            }
        });

        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginsBoth.this,PatientLoginPage.class);
                startActivity(intent);
            }
        });


    }
}
