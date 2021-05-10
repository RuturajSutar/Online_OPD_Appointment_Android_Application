package com.example.onlineopdappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApppointmentActivity extends AppCompatActivity {

    Intent intent;
    public String email,cityname;
    TextView one,two,three,four,five;
    Button button;
    RatingBar ratingBar;
    DatabaseReference mRef;
    public String name,Email,qualification,spec,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apppointment);

        intent = getIntent();
        email = intent.getStringExtra("email");
        cityname = intent.getStringExtra("cityname");
        Toast.makeText(this, cityname+""+email, Toast.LENGTH_SHORT).show();

        one=(TextView)findViewById(R.id.textView1);
        two=(TextView)findViewById(R.id.textView2);
        three=(TextView)findViewById(R.id.textView3);
        four=(TextView)findViewById(R.id.textView4);
        five=(TextView)findViewById(R.id.textView5);

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

            }
        });

        button=(Button)findViewById(R.id.button);
        String removeDot=email.replace(".","");
        String removeAt=removeDot.replace("@","");
        mRef = FirebaseDatabase.getInstance().getReference("Hospital").child(cityname).child(removeAt);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name=dataSnapshot.child("Hospital_Name").getValue().toString();
                Email=dataSnapshot.child("Hospital_Email").getValue().toString();
                qualification=dataSnapshot.child("Hospital_Cityname").getValue().toString();
                spec =dataSnapshot.child("Hospital_Address").getValue().toString();
                gender=dataSnapshot.child("Hospital_Mobile").getValue().toString();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one.setText("Hospital Name     :"+name);
                two.setText("Hospital Email     :"+Email);
                three.setText("Hospital Cityname     :"+qualification);
                four.setText("Hospital Address     :"+spec);
                five.setText("Hospital Mobile     :"+gender);
                Toast.makeText(ApppointmentActivity.this, "Your Appointment is successfully booked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
