package com.example.onlineopdappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientOTPVerification extends AppCompatActivity {

    Button checkOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_otpverification);


        checkOTP=(Button)findViewById(R.id.button);
        checkOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientOTPVerification.this,PatientLoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
