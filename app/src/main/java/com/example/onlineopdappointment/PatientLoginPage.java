package com.example.onlineopdappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientLoginPage extends AppCompatActivity {


    TextView registerPatient;
    Button patientLogin;
    Spinner citySpinner;
    public String cityName;
    EditText pPassword,pMobile;
    DatabaseReference mRef;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login_page);

        registerPatient=(TextView)findViewById(R.id.textView);
        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=PatientRegisterPage.makeIntent(PatientLoginPage.this);
                startActivity(intent);
                finish();
            }
        });



        citySpinner=(Spinner)findViewById(R.id.spinner1);
        List<String> listone=new ArrayList<String>();
        listone.add("Select_City");
        listone.add("Karad");
        listone.add("Islampur");
        listone.add("Satara");
        listone.add("Miraj");
        listone.add("Sangli");
        ArrayAdapter<String> arrayAdapterone=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listone);
        arrayAdapterone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(arrayAdapterone);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySpinner.setSelection(position);
                Log.i("info","Position"+citySpinner.getItemAtPosition(position).toString());
                cityName=citySpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pMobile=(EditText)findViewById(R.id.editText1);
        pPassword=(EditText)findViewById(R.id.editText2);



        patientLogin=(Button)findViewById(R.id.button);
        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String myMobile=pMobile.getText().toString();
                final String myPassword=pPassword.getText().toString();
                TextView errorText2 = (TextView)citySpinner.getSelectedView();

                if (myMobile.isEmpty()){
                    pMobile.setError("Please Enter Email");
                    pMobile.requestFocus();
                }
                else if (myPassword.isEmpty()){
                    pPassword.setError("Please Enter Password");
                    pPassword.requestFocus();
                }
                else if(cityName.equals("Select_City"))
                {
                    errorText2.setError("Please Enter Your City");
                    errorText2.requestFocus();
                }
                else if(!(myMobile.isEmpty() && myPassword.isEmpty()))
                {
                    mRef= FirebaseDatabase.getInstance().getReference().child("Patients").child(cityName);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String username=dataSnapshot.child(myMobile).child("Patient_Mobile").getValue().toString();
                            String password=dataSnapshot.child(myMobile).child("Patient_Password").getValue().toString();

                            if (myMobile.equals(username) && myPassword.equals(password)){
                                Intent intent=new Intent(PatientLoginPage.this,PaitientDashboard.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(PatientLoginPage.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(PatientLoginPage.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }





            }
        });
    }
}
