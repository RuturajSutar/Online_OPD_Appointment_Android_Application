package com.example.onlineopdappointment;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NBHospitalLoginDoctor extends AppCompatActivity {

    Button doctorlogin;
    TextView registerDoctor;
    EditText dEmail,dPassword;
    DatabaseReference mRef;
    public  String myEmail,myPassword;
    public String removeDot,removeAt;
    Intent intent;
    public String emailExtra,cityExtra;
    Spinner citySpinner;
    public String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbhospital_login_doctor);

        doctorlogin = (Button)findViewById(R.id.button);

        intent=getIntent();
        emailExtra=intent.getStringExtra("Email");
        cityExtra=intent.getStringExtra("City");


        dEmail=(EditText)findViewById(R.id.editText1);
        dPassword=(EditText)findViewById(R.id.editText2);

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


        doctorlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEmail=dEmail.getText().toString();
                myPassword=dPassword.getText().toString();

                removeDot =myEmail.replace(".","");
                removeAt=removeDot.replace("@","");

                if (myEmail.isEmpty()){
                    dEmail.setError("Please Enter Email");
                    dEmail.requestFocus();
                    
                }
                else if (myPassword.isEmpty()){
                    dPassword.setError("Please Enter Password");
                    dPassword.requestFocus();

                }
                else if (!(myEmail.isEmpty() && myPassword.isEmpty())){

                    Toast.makeText(NBHospitalLoginDoctor.this, ""+cityName, Toast.LENGTH_SHORT).show();
                    mRef= FirebaseDatabase.getInstance().getReference().child("Doctors");
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String username=dataSnapshot.child(cityName).child(removeAt).child("Doctor_Email").getValue().toString();
                            String password=dataSnapshot.child(cityName).child(removeAt).child("Doctor_Password").getValue().toString();



                            if (myEmail.equals(username) && myPassword.equals(password)){
                                Intent intent=new Intent(NBHospitalLoginDoctor.this,DoctorDashboard.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(NBHospitalLoginDoctor.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(NBHospitalLoginDoctor.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    });





                }


            }
        });

        registerDoctor=(TextView)findViewById(R.id.textView);
        registerDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NBHospitalLoginDoctor.this,NBHospitalRegisterDoctor.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
