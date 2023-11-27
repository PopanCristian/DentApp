package com.example.dentaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<com.example.dentaapp.Appointment> {

    public AppointmentAdapter(Context context, List<com.example.dentaapp.Appointment> appointments) {
        super(context, R.layout.appointment_item, appointments); // Asigurați-vă că aveți un layout cu numele `appointment_item.xml`.
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_item, parent, false);
        }

        Appointment appointment = getItem(position);

        TextView tvDoctorUsername = convertView.findViewById(R.id.tvDoctorUsername);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvStartTime = convertView.findViewById(R.id.tvStartTime);
        TextView tvEndTime = convertView.findViewById(R.id.tvEndTime);

        tvDoctorUsername.setText(appointment.getDoctorUsername());
        tvDate.setText(appointment.getDate());
        tvStartTime.setText(appointment.getStartTime());
        tvEndTime.setText(appointment.getEndTime());

        return convertView;
    }
}



