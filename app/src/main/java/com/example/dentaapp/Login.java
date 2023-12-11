package com.example.dentaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username, password,doctorname,doctorpassword;
    Button login;
    ImageButton back;
    DBHelper DB;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        doctorname = (EditText) findViewById(R.id.doctorNameLogIn);
        doctorpassword = (EditText) findViewById(R.id.password2);
        login = (Button) findViewById(R.id.loginbutton);
        back = (ImageButton) findViewById(R.id.backbutton);
        DB = new DBHelper(this);


        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String doctor = doctorname.getText().toString();
                String pass2 = doctorpassword.getText().toString();

                if (TextUtils.isEmpty(user) && TextUtils.isEmpty(doctor)) {
                    Toast.makeText(Login.this, "Completează câmpurile corespunzătoare rolului tău!", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(user) && TextUtils.isEmpty(doctor)) {
                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(Login.this, "Completează toate câmpurile de pacient!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if (checkuserpass) {
                            Toast.makeText(Login.this, "Te-ai logat cu succes!", Toast.LENGTH_SHORT).show();
                            String username = user;
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("USERNAME", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Logare eșuată!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (!TextUtils.isEmpty(doctor) && TextUtils.isEmpty(user)) {
                    if (TextUtils.isEmpty(pass2)) {
                        Toast.makeText(Login.this, "Completează toate câmpurile de doctor!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean checkDoctorPass = DB.checkDoctorUsernamePassword(doctor, pass2);
                        if (checkDoctorPass) {
                            Toast.makeText(Login.this, "Te-ai logat ca doctor cu succes!", Toast.LENGTH_SHORT).show();
                            String doctorname = doctor;
                            Intent intent = new Intent(getApplicationContext(), DoctorHomeActivity.class);
                            intent.putExtra("USERNAME", doctorname);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Logare eșuată pentru doctor!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Terminați activitatea curentă
                finish();
            }
        });


    }

}