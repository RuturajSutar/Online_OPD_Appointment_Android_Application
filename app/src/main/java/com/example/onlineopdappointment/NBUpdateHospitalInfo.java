package com.example.onlineopdappointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class NBUpdateHospitalInfo extends AppCompatActivity {


    Button updateHospital;
    String city,emailstr;
    Spinner specialitySpinner;
    Spinner citySpinner;
    String specialityName;
    private EditText name,email,mobno,password,address,speciality;
    DatabaseReference databaseReference,mRef;





    public static Intent makeIntent(Context context){
        return new Intent(context,NBUpdateHospitalInfo.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbupdate_hospital_info);

        updateHospital=(Button)findViewById(R.id.button);
        updateHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
