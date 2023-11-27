package com.example.dentaapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> doctorListFull;
    private DBHelper dbHelper;

    public DoctorAdapter(Context context, int resource, DBHelper dbHelper) {
        super(context, resource);
        this.dbHelper = dbHelper;
        doctorListFull = dbHelper.getAllDoctorNames(); // Obțineți toate numele doctorilor
    }

    @Override
    public Filter getFilter() {
        return doctorFilter;
    }

    private Filter doctorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> suggestions = new ArrayList<>();

            if (constraint != null && !constraint.toString().isEmpty()) {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String doctorName : doctorListFull) {
                    if (doctorName.toLowerCase().contains(filterPattern)) {
                        suggestions.add(doctorName);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return (String) resultValue;
        }
    };
}
