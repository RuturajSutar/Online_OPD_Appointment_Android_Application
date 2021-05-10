package com.example.onlineopdappointment;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBHospitalRegisterDoctor extends AppCompatActivity {

    Spinner genderSpinner;
    Button regDoctor;
    TextView loginDoctor;
    Spinner specialitySpinner;
    public String genderName,specialityName;
    EditText dName,dEmail,dPassword,dMobile,dAge,dQual;
    DatabaseReference mRef;
    public String cityName;

    Intent intent;
    public String emailExtra,cityExtra;


    public static Intent makeIntent(Context context){
        return new Intent(context,NBHospitalRegisterDoctor.class);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbhospital_register_doctor);


        intent=getIntent();
        emailExtra=intent.getStringExtra("Email");
        cityExtra=intent.getStringExtra("City");


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





        genderSpinner=(Spinner)findViewById(R.id.spinner);
        List<String> listone=new ArrayList<String>();
        listone.add("Select_Gender");
        listone.add("Male");
        listone.add("Female");
        listone.add("Other");
        ArrayAdapter<String> arrayAdapterone=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listone);
        arrayAdapterone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapterone);

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

        dName=(EditText)findViewById(R.id.editText1);
        dEmail=(EditText)findViewById(R.id.editText2);
        dPassword=(EditText)findViewById(R.id.editText7);
        dMobile=(EditText)findViewById(R.id.editText3);
        dAge=(EditText)findViewById(R.id.editText4);
        dQual=(EditText)findViewById(R.id.editText6);



        regDoctor=(Button)findViewById(R.id.button);
        regDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myName=dName.getText().toString();
                String myEmail=dEmail.getText().toString();
                String myPassword=dPassword.getText().toString();
                String myMobile=dMobile.getText().toString();
                String myAge=dAge.getText().toString();
                String myQuali=dQual.getText().toString();
                String doctor="Doctors";

                TextView errorText1 = (TextView)specialitySpinner.getSelectedView();
                TextView errorText2 = (TextView)genderSpinner.getSelectedView();

                String removeDot=myEmail.replace(".","");
                String removeAt=removeDot.replace("@","");


                if (myName.isEmpty()){
                    dName.setError("Please Enter Hospital Name");
                    dName.requestFocus();
                }
                else if (myEmail.isEmpty()){
                    dEmail.setError("Please Enter Email");
                    dEmail.requestFocus();
                }
                else if (myPassword.isEmpty()){
                    dPassword.setError("Please Enter Password");
                    dPassword.requestFocus();
                }
                else if (myMobile.isEmpty()){
                    dMobile.setError("Please Enter Your Mobile Number");
                    dMobile.requestFocus();

                }
                else if (myAge.isEmpty()){
                    dAge.setError("Please Enter Address");
                    dAge.requestFocus();

                }
                else if (myQuali.isEmpty()){
                    dQual.setError("Please Enter Address");
                    dQual.requestFocus();

                }
                else if(specialityName.equals("Select_Speciality"))
                {
                    errorText1.setError("Please Enter Your Speciality");
                    errorText1.requestFocus();
                }

                else if(genderName.equals("Select_Gender"))
                {
                    errorText2.setError("Please Enter Your Gender");
                    errorText2.requestFocus();
                }

                else if(!(myName.isEmpty() && myEmail.isEmpty() && myPassword.isEmpty() && myMobile.isEmpty() && myAge.isEmpty() && myQuali.isEmpty()))
                {
                    Intent intent=getIntent();
                    cityName=intent.getStringExtra("city");
                    mRef= FirebaseDatabase.getInstance().getReference().child("Doctors");
                    Map<String,Object> insertValues=new HashMap<>();
                    insertValues.put("Doctor_Name",myName);
                    insertValues.put("Doctor_Email",myEmail);
                    insertValues.put("Doctor_Password",myPassword);
                    insertValues.put("Doctor_Mobile",myMobile);
                    insertValues.put("Doctor_Age",myAge);
                    insertValues.put("Doctor_Qualification",myQuali);
                    insertValues.put("Doctor_Speciality",specialityName);
                    insertValues.put("Doctor_Gender",genderName);
                    mRef.child(cityName).child(removeAt).setValue(insertValues);
                    startActivity(new Intent(getApplicationContext(),NBHospitalLoginDoctor.class));
                    finish();
                }




            }
        });

        loginDoctor=(TextView)findViewById(R.id.textView);
        loginDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NBHospitalRegisterDoctor.this,NBHospitalLoginDoctor.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
