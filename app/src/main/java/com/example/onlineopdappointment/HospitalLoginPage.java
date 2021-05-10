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

public class HospitalLoginPage extends AppCompatActivity {

    TextView registerHospital;
    Button hospitalLogin;
    EditText hEmail,hPassword;
    DatabaseReference mRef;
    Spinner citySpinner;
    public String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login_page);


        hEmail=(EditText)findViewById(R.id.editText1);
        hPassword=(EditText)findViewById(R.id.editText2);

        hospitalLogin=(Button)findViewById(R.id.button);

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



        registerHospital=(TextView)findViewById(R.id.textView);
        registerHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HospitalRegistrationPage.class));
            }
        });



        hospitalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String myEmail=hEmail.getText().toString();
                final String myPassword=hPassword.getText().toString();

                String removeDot=myEmail.replace(".","");
                final String removeAt=removeDot.replace("@","");

                TextView errorText2 = (TextView)citySpinner.getSelectedView();

                if (myEmail.isEmpty()){
                    hEmail.setError("Please Enter Email");
                    hEmail.requestFocus();
                }
                else if (myPassword.isEmpty()){
                    hPassword.setError("Please Enter Password");
                    hPassword.requestFocus();
                }
                else if(cityName.equals("Select_City"))
                {
                    errorText2.setError("Please Enter Your City");
                    errorText2.requestFocus();
                }
                else if(!(myEmail.isEmpty() && myPassword.isEmpty()))
                {
                    mRef=FirebaseDatabase.getInstance().getReference().child("Hospital").child(cityName);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String username=dataSnapshot.child(removeAt).child("Hospital_Email").getValue().toString();
                            String password=dataSnapshot.child(removeAt).child("Hospital_Password").getValue().toString();
                            String hospitalName = dataSnapshot.child(removeAt).child("Hospital_Name").getValue().toString();

//                            String withoutDot=username.replace(".","");
//                            String withoutAt=withoutDot.replace("@","");

                            if (myEmail.equals(username) && myPassword.equals(password)){
                                Intent intent=new Intent(HospitalLoginPage.this,HospitalDashboard.class);
                                intent.putExtra("city",cityName);
                                intent.putExtra("name",hospitalName);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(HospitalLoginPage.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(HospitalLoginPage.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
