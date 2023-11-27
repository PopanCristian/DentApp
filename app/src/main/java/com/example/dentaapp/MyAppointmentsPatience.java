package com.example.dentaapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.example.dentaapp.Appointment;
import com.example.dentaapp.AppointmentAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyAppointmentsPatience extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView listViewAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointments_patience);

        dbHelper = new DBHelper(this);
        listViewAppointments = findViewById(R.id.listViewAppointments);

        //String username = getCurrentUsername();
        //if (username != null && !username.isEmpty()) {
        //List<com.example.dentaapp.Appointment> appointmentsList = dbHelper.getAppointmentsForUser(username);
        //AppointmentAdapter adapter = new AppointmentAdapter(this, appointmentsList);
        //listViewAppointments.setAdapter(adapter);
//        } else {
//            Toast.makeText(this, "Nu s-a putut obține username-ul utilizatorului.", Toast.LENGTH_LONG).show();
//        }
    }
}






