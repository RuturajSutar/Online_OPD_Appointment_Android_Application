package com.example.onlineopdappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HospitalDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public String intentEmail,intentCity;
    public String cityName,hospitalName;
    TextView hospName;
    public Button button;
    ImageView imageView,profilePicture;



    public void toast(View view){
        Toast.makeText(HospitalDashboard.this, "Hello", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode ==RESULT_OK && data != null &&  data.getData() != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView = (ImageView) findViewById(R.id.imageView2);
                profilePicture = (ImageView) findViewById(R.id.navigationImage);
                imageView.setImageBitmap(bitmap);
                profilePicture.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);

        button = (Button) findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
            }



        });

        Intent intent = getIntent();
        String HospName = intent.getStringExtra("name");

        hospName = (TextView) findViewById(R.id.hospitalName);
        hospName.setText(HospName);



            findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

//        intent=getIntent();
//        intentEmail=intent.getStringExtra("Email");
//        intentCity=intent.getStringExtra("City");




        //Nevigation Drawer Code Starts


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                    Intent intent3=new Intent(HospitalDashboard.this,HospitalDashboard.class);
                    startActivity(intent3);
                }
                else if (id == R.id.nav_registerdoctor) {

                    Intent intent=getIntent();
                    cityName=intent.getStringExtra("city");
                    Intent intent1= new Intent(getApplicationContext(),NBHospitalRegisterDoctor.class);
                    intent1.putExtra("city",cityName);
                    startActivity(intent1);
                }
                else if (id == R.id.nav_logindoctor) {

                    Intent intent2=new Intent(HospitalDashboard.this,NBHospitalLoginDoctor.class);
                   startActivity(intent2);
                }
                else if (id == R.id.nav_history) {


                }
                else if (id == R.id.nav_logout) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(HospitalDashboard.this);

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
        //Nevigation Drawer Code Ends
    }



    //Nevigation Drawer Code Starts

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hospital_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Nevigation Drawer Code Ends

}
