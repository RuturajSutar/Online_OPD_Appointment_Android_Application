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

public class PatientRegisterPage extends AppCompatActivity {

    Spinner genderSpinner;
    TextView backToLogin;
    Button register;
    EditText pName,pPassword,pMobile,pAge,pAddress;
    public String cityName,genderName;
    DatabaseReference mRef;
    Spinner citySpinner;


    public static Intent makeIntent(Context context){
        return new Intent(context,PatientRegisterPage.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_page);




        genderSpinner=(Spinner)findViewById(R.id.spinner);
        List<String> list=new ArrayList<String>();
        list.add("Select_Gender");
        list.add("Male");
        list.add("Female");
        list.add("Other");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSpinner.setSelection(position);
                Log.i("info","Position"+genderSpinner.getItemAtPosition(position).toString());
                genderName=genderSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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





        backToLogin=(TextView)findViewById(R.id.textView);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pName=(EditText)findViewById(R.id.editText1);
        pMobile=(EditText)findViewById(R.id.editText3);
        pPassword=(EditText)findViewById(R.id.editText2);
        pAge=(EditText)findViewById(R.id.editText4);
        pAddress=(EditText)findViewById(R.id.editText5);


        register=(Button)findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String myName=pName.getText().toString();
                String myMobile=pMobile.getText().toString();
                String myPassword=pPassword.getText().toString();
                String myAge=pAge.getText().toString();
                String myAddress=pAddress.getText().toString();
                String patient="Patients";

                TextView errorText1 = (TextView)genderSpinner.getSelectedView();
                TextView errorText2 = (TextView)citySpinner.getSelectedView();




                if (myName.isEmpty()){
                    pName.setError("Please Enter Hospital Name");
                    pName.requestFocus();
                }
                else if (myMobile.isEmpty()){
                    pMobile.setError("Please Enter Email");
                    pMobile.requestFocus();
                }
                else if (myPassword.isEmpty()){
                    pPassword.setError("Please Enter Password");
                    pPassword.requestFocus();
                }
                else if (myAge.isEmpty()){
                    pAge.setError("Please Enter Your Mobile Number");
                    pAge.requestFocus();

                }
                else if (myAddress.isEmpty()){
                    pAddress.setError("Please Enter Address");
                    pAddress.requestFocus();

                }
                else if(genderName.equals("Select_Gender"))
                {
                    errorText1.setError("Please Enter Your gender");
                    errorText1.requestFocus();
                }
                else if(cityName.equals("Select_City"))
                {
                    errorText2.setError("Please Enter Your City");
                    errorText2.requestFocus();
                }

                else if(!(myName.isEmpty() && myMobile.isEmpty() && myPassword.isEmpty() && myAge.isEmpty() && myAddress.isEmpty()))
                {
                    mRef= FirebaseDatabase.getInstance().getReference().child(patient).child(cityName);
                    Map<String,Object> insertValues=new HashMap<>();
                    insertValues.put("Patient_Name",myName);
                    insertValues.put("Patient_Mobile",myMobile);
                    insertValues.put("Patient_Password",myPassword);
                    insertValues.put("Patient_Age",myAge);
                    insertValues.put("Patient_Address",myAddress);
                    insertValues.put("Hospital_Cityname",cityName);
                    insertValues.put("Patient_Gender",genderName);
                    mRef.child(myMobile).setValue(insertValues);
                    Intent intent=new Intent(PatientRegisterPage.this,PatientOTPVerification.class);
                    startActivity(intent);
                    finish();
                }





            }
        });

    }
}
