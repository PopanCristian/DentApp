package com.example.dentaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AppointmentAdapterDoctor extends ArrayAdapter<AppointmentForDoctor> {

    public AppointmentAdapterDoctor(@NonNull Context context, List<AppointmentForDoctor> appointments) {
        super(context, R.layout.appointment_item, appointments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_item, parent, false);
        }

        AppointmentForDoctor appointment = getItem(position);

        TextView tvPatientUsername = convertView.findViewById(R.id.tvPatientUsername);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvStartTime = convertView.findViewById(R.id.tvStartTime);
        TextView tvEndTime = convertView.findViewById(R.id.tvEndTime);

        tvPatientUsername.setText(appointment.getPatientUsername());
        tvDate.setText(appointment.getDate());
        tvStartTime.setText(appointment.getStartTime());
        tvEndTime.setText(appointment.getEndTime());

        return convertView;
    }
}

