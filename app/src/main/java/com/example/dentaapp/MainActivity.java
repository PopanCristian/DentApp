package com.example.dentaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText username, password, confirmpass,email;
    Button buttonSignUp, buttonLogIn, buttonDoctorAccount;
    DBHelper MyDB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        confirmpass = findViewById(R.id.confirmpass);
        buttonSignUp = (Button) findViewById(R.id.createAccount);
        buttonLogIn = (Button) findViewById(R.id.haveAcount);
        buttonDoctorAccount = (Button) findViewById(R.id.createDoctorAccount);
        email = findViewById(R.id.useremail);
        MyDB = new DBHelper(this);


        buttonSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = confirmpass.getText().toString();
                String emaill = email.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(emaill)) {
                    Toast.makeText(MainActivity.this, "Este nevoie sa completezi toate campurile !", Toast.LENGTH_SHORT).show();
                } else {
                    Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(yahoo\\.com|gmail\\.com)$");
                    Matcher matcher = pattern.matcher(emaill);
                    if (!matcher.matches()) {
                        Toast.makeText(MainActivity.this, "Introdu o adresă de email validă!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(pass.equals(repass)) {
                        boolean checkuser = MyDB.checkusername(user);
                        boolean checkemail = MyDB.checkEmail(emaill);
                        if(!checkuser && !checkemail) {
                            boolean insert = MyDB.insertData(user, pass, emaill);
                            if(insert) {
                                Toast.makeText(MainActivity.this, "Cont creat cu succes !", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", String.valueOf(username)); // 'username' este numele de utilizator al utilizatorului care se autentifică
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Nu s-a putut realiza crearea !", Toast.LENGTH_SHORT).show();
                            }
                        } else if(checkuser==true){
                            Toast.makeText(MainActivity.this, "Username-ul este deja folosit ! Modificati username !", Toast.LENGTH_SHORT).show();
                        } else {Toast.makeText(MainActivity.this, "Email-ul este deja folosit ! Modificati email !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Reintroduceti parola corecta !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonDoctorAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DoctorSignUp.class);
                    startActivity(intent);
                }
        });
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

}