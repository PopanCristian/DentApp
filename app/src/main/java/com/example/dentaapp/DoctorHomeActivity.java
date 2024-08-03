package com.example.dentaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DoctorHomeActivity extends AppCompatActivity {
    private ListView listViewAppointments;
    DBHelper dbHelper;

    Button btnProgramarileMele, btnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);


        btnProgramarileMele = findViewById(R.id.btnMyAppointments);
        btnProfil = findViewById(R.id.btnProfile);
        listViewAppointments = findViewById(R.id.listViewAppointments);
        dbHelper = new DBHelper(this);

        btnProgramarileMele.setSelected(true);
        btnProfil.setSelected(false);
        btnProgramarileMele.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
        btnProfil.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));

        String doctorUsername = getIntent().getStringExtra("USERNAME");
        String doctorName = dbHelper.getDoctorNameFromUsername(doctorUsername);


        /*if (doctorName != null && !doctorName.isEmpty()) {
            List<AppointmentForDoctor> appointmentsList = dbHelper.getAppointmentsForDoctor(doctorName);
            AppointmentAdapterDoctor adapter = new AppointmentAdapterDoctor(this, appointmentsList);
            listViewAppointments.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nu s-a putut obține username-ul doctorului.", Toast.LENGTH_LONG).show();
        }*/

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this, ProfilePatience.class);
                startActivity(intent);
            }
        });
    }

}
