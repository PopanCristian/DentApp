package com.example.dentaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePatience extends AppCompatActivity {
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_patience);

        dbHelper = new DBHelper(this);

        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        Button btnSchedule = findViewById(R.id.btnSchedule);
        Button btnMyAppointemnts = findViewById(R.id.btnMyAppointments);
        Button btnProfile = findViewById(R.id.btnProfile);



        btnSchedule.setSelected(false);
        btnMyAppointemnts.setSelected(false);
        btnProfile.setSelected(true);
        btnSchedule.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));
        btnMyAppointemnts.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));
        btnProfile.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
        String username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.isEmpty()) {
            textViewUsername.setText(username);

            String email = dbHelper.getEmailForUsername(username);

            if (email != null) {
                textViewEmail.setText(email);
            } else {
                textViewEmail.setText("Email nu este disponibil.");
            }
        } else {
            textViewUsername.setText("Username nu este disponibil.");
            textViewEmail.setText("Email nu este disponibil.");
        }

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logica pentru logout
                Intent intent = new Intent(ProfilePatience.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePatience.this, HomeActivity.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);

                startActivity(intent);
            }
        });
        btnMyAppointemnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePatience.this, MyAppointmentsPatience.class);
                String username = getIntent().getStringExtra("USERNAME");
                Log.d("SearchActivity", "Username la ProgramarileMele: " + username);

                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}
