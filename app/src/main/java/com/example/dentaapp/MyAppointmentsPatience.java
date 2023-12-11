package com.example.dentaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.dentaapp.Appointment;
import com.example.dentaapp.AppointmentAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyAppointmentsPatience extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView listViewAppointments;
    Button btnCauta, btnProgramarileMele, btnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_appointments_patience);

        listViewAppointments = findViewById(R.id.listViewAppointments);
        dbHelper = new DBHelper(this);
        btnCauta = findViewById(R.id.btnSchedule);
        btnProgramarileMele = findViewById(R.id.btnMyAppointments);
        btnProfil = findViewById(R.id.btnProfile);

        btnCauta.setSelected(false);
        btnProgramarileMele.setSelected(true);
        btnProfil.setSelected(false);
        btnCauta.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));
        btnProgramarileMele.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
        btnProfil.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));

        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAppointmentsPatience.this, HomeActivity.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAppointmentsPatience.this, ProfilePatience.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        String username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.isEmpty()) {
            List<Appointment> appointmentsList = dbHelper.getAppointmentsForUser(username);
            AppointmentAdapter adapter = new AppointmentAdapter(this, appointmentsList);
            listViewAppointments.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nu s-a putut obține username-ul utilizatorului.", Toast.LENGTH_LONG).show();
        }
    }
}






