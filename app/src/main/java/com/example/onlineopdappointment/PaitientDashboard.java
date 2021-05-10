package com.example.onlineopdappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class PaitientDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DatabaseReference databaseReference,databaseReference2;
    Users users;

    ArrayList<String> itemsForHospital = new ArrayList<>();
    ArrayList<String> omkar = new ArrayList<>();
    SpinnerDialog spinnerForHospital;


         Spinner spinner;
        public String cityname;
        public String email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paitient_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spinner = (Spinner) findViewById(R.id.citySpinnerUserDashboard);

        List<String> listone=new ArrayList<String>();
        listone.add("Select_City");
        listone.add("Karad");
        listone.add("Islampur");
        listone.add("Satara");
        listone.add("Miraj");
        listone.add("Sangli");

        ArrayAdapter<String> arrayAdapterone=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,listone);
        arrayAdapterone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterone);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
//                Log.i("info","Position"+spinner.getItemAtPosition(position).toString());
//                Toast.makeText(PaitientDashboard.this, ""+spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                  cityname=spinner.getItemAtPosition(position).toString();
                //Toast.makeText(PaitientDashboard.this, ""+cityname, Toast.LENGTH_SHORT).show();
                itemsForHospital.clear();
                omkar.clear();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital").child(cityname);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            String str = ds.child("Hospital_Name").getValue().toString();
                            String str1 = ds.child("Hospital_Speciality").getValue().toString();
                            String str2 = ds.child("Hospital_Email").getValue().toString();
                            itemsForHospital.add("Hospital Name       :"+str+"\nHospital Speciality :"+str1);
                            omkar.add(""+str2);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Doctor").child(cityname);
//                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds:dataSnapshot.getChildren()){
//                            itemsForHospital.clear();
//                            String str = ds.child("Hospital_Name").getValue().toString();
//                            String str1 = ds.child("Hospital_Speciality").getValue().toString();
//                            itemsForHospital.add("Hospital Name       :"+str+"\nHospital Speciality :"+str1);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        findViewById(R.id.searchByHospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaitientDashboard.this, "done", Toast.LENGTH_SHORT).show();
                spinnerForHospital = new SpinnerDialog(PaitientDashboard.this,itemsForHospital,"select item");
                spinnerForHospital.showSpinerDialog();
                spinnerForHospital.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String s, int i) {
                        Toast.makeText(PaitientDashboard.this, " name = "+s+"\n Email ="+omkar.get(i), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ApppointmentActivity.class);
                        intent.putExtra("email",omkar.get(i));
                        intent.putExtra("cityname",cityname);
                        startActivity(intent);
                    }
                });


            }
        });










        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {


                }
                else if (id == R.id.nav_update) {


                }
                else if (id == R.id.nav_appointment) {


                }

                else if (id == R.id.nav_logout) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(PaitientDashboard.this);

                    builder.setMessage("Are you sure logout..?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }


                return false;
            }


        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.paitient_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
