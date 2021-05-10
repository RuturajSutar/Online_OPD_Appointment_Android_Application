package com.example.onlineopdappointment;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalRegistrationPage extends AppCompatActivity {

    TextView backToLogin;
    Spinner specialitySpinner;
    Spinner citySpinner;
    EditText hName,hEmail,hPassword,hMobile,hAddress;
    public String cityName,specialityName;
    Button registerHospital;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;



    public static Intent makeIntent(Context context){
        return new Intent(context,HospitalRegistrationPage.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration_page);



        specialitySpinner=(Spinner)findViewById(R.id.specialityspinner);
        List<String> list=new ArrayList<String>();
        list.add("Select_Speciality");
        list.add("Dentist");
        list.add("OrthoSurgeon");
        list.add("NeuroSurgeon");
        list.add("Physician");
        list.add("Dermatologist");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialitySpinner.setAdapter(arrayAdapter);

        specialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialitySpinner.setSelection(position);
                Log.i("info","Position"+specialitySpinner.getItemAtPosition(position).toString());
                specialityName=specialitySpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner=(Spinner)findViewById(R.id.cityspinner);
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



        backToLogin=(TextView)findViewById(R.id.textView);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hName=(EditText)findViewById(R.id.editText1);
        hEmail=(EditText)findViewById(R.id.editText2);
        hPassword=(EditText)findViewById(R.id.editText6);
        hMobile=(EditText)findViewById(R.id.editText3);
        hAddress=(EditText)findViewById(R.id.editText4);

        registerHospital=(Button)findViewById(R.id.button);
        registerHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myName=hName.getText().toString();
                String myEmail=hEmail.getText().toString();
                String myPassword=hPassword.getText().toString();
                String myMobile=hMobile.getText().toString();
                String myAddress=hAddress.getText().toString();
                String hospital="Hospital";

                TextView errorText1 = (TextView)specialitySpinner.getSelectedView();
                TextView errorText2 = (TextView)citySpinner.getSelectedView();

                String removeDot=myEmail.replace(".","");
                String removeAt=removeDot.replace("@","");

                if (myName.isEmpty()){
                    hName.setError("Please Enter Hospital Name");
                    hName.requestFocus();
                }
                else if (myEmail.isEmpty()){
                    hEmail.setError("Please Enter Email");
                    hEmail.requestFocus();
                }
                else if (myPassword.isEmpty()){
                    hPassword.setError("Please Enter Password");
                    hPassword.requestFocus();
                }
                else if (myMobile.isEmpty()){
                    hMobile.setError("Please Enter Your Mobile Number");
                    hMobile.requestFocus();

                }
                else if (myAddress.isEmpty()){
                    hAddress.setError("Please Enter Address");
                    hAddress.requestFocus();

                }
                else if(specialityName.equals("Select_Speciality"))
                {
                    errorText1.setError("Please Enter Your Speciality");
                    errorText1.requestFocus();
                }

                else if(cityName.equals("Select_City"))
                {
                    errorText2.setError("Please Enter Your City");
                    errorText2.requestFocus();
                }

                else if(!(myName.isEmpty() && myEmail.isEmpty() && myPassword.isEmpty() && myMobile.isEmpty() && myAddress.isEmpty()))
                {
                    mRef=FirebaseDatabase.getInstance().getReference().child(hospital).child(cityName);
                    Map<String,Object> insertValues=new HashMap<>();
                    insertValues.put("Hospital_Name",myName);
                    insertValues.put("Hospital_Email",myEmail);
                    insertValues.put("Hospital_Password",myPassword);
                    insertValues.put("Hospital_Mobile",myMobile);
                    insertValues.put("Hospital_Address",myAddress);
                    insertValues.put("Hospital_Cityname",cityName);
                    insertValues.put("Hospital_Speciality",specialityName);
                    mRef.child(removeAt).setValue(insertValues);
                    Intent intent=new Intent(HospitalRegistrationPage.this,HospitalLoginPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }
}
