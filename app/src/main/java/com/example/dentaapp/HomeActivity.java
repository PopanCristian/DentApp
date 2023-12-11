package com.example.dentaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private String selectedDate;
    private String selectedTime;


    DBHelper DB;
    Button btnCauta, btnProgramarileMele, btnProfil, btnSelectDate, btnSelectTime,buttonSearch;
    AutoCompleteTextView autoCompleteTextView, btnDoctor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        autoCompleteTextView = findViewById(R.id.SelectArea);
        DB = new DBHelper(this);
        btnCauta = findViewById(R.id.btnSchedule);
        btnProgramarileMele = findViewById(R.id.btnMyAppointments);
        btnProfil = findViewById(R.id.btnProfile);
        btnSelectDate = findViewById(R.id.buttonSelectDate);
        btnSelectTime = findViewById(R.id.buttonSelectTiming);
        buttonSearch = findViewById(R.id.buttonSearch);




        btnCauta.setSelected(true);
        btnProgramarileMele.setSelected(false);
        btnProfil.setSelected(false);
        btnCauta.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
        btnProgramarileMele.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));
        btnProfil.setBackgroundColor(getResources().getColor(R.color.unselected_button_color));


        String[] judete = {"Alba", "Arad", "Arges", "Bacau", "Bihor", "Bistrita-Nasaud", "Botosani", "Braila", "Brasov", "Buzau",
                "Calarasi", "Caras-Severin", "Cluj", "Constanta", "Covasna", "Dambovita", "Dolj", "Galati", "Giurgiu",
                "Gorj", "Harghita", "Hunedoara", "Ialomita", "Iasi", "Ilfov", "Maramures", "Mehedinti", "Mures",
                "Neamt", "Olt", "Prahova", "Salaj", "Satu Mare", "Sibiu", "Suceava", "Teleorman", "Timis", "Tulcea",
                "Vaslui", "Valcea", "Vrancea"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                judete
        );

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(2);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String judetSelectat = adapter.getItem(position);
        });



        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedArea = getSelectedArea();
                String selectedDoctorUsername = getSelectedDoctorUsername();
                String selectedDate = getSelectedDate();
                String selectedTime = getSelectedTime();

                String username = getIntent().getStringExtra("USERNAME");
                Log.d("SearchActivity", "Username la buttonsearch: " + username);
                validateAndAddAppointment(username,selectedDoctorUsername, selectedDate, selectedTime);
            }
        });
        AutoCompleteTextView autoCompleteTextViewDoctor = findViewById(R.id.editTextSearchDoctor);
        DoctorAdapter doctorAdapter = new DoctorAdapter(this, android.R.layout.simple_dropdown_item_1line, DB);
        autoCompleteTextViewDoctor.setAdapter(doctorAdapter);

        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnProgramarileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyAppointmentsPatience.class);
                String username = getIntent().getStringExtra("USERNAME");
                Log.d("SearchActivity", "Username la ProgramarileMele: " + username);

                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfilePatience.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);

                startActivity(intent);
            }
        });
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                                btnSelectDate.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        Button buttonSelectTime = findViewById(R.id.buttonSelectTiming);
        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(HomeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                buttonSelectTime.setText(selectedTime);
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });

//        private void performLogout () {
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//            finish();
//            Toast.makeText(HomeActivity.this, "Deconectarea a avut succes !", Toast.LENGTH_SHORT).show();
//
//        }

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HomeActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear, dayOfMonth);
                        btnSelectDate.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                HomeActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        btnSelectTime.setText(formattedTime);
                    }
                }, hour, minute, true); // 'true' pentru formatul de 24 de ore
        timePickerDialog.show();
    }
    private String getSelectedArea() {
        // Presupunem că aveți un Spinner pentru zone
        AutoCompleteTextView  areaSpinner = findViewById(R.id.SelectArea); // Înlocuiți cu ID-ul real al Spinner-ului pentru zone
        return areaSpinner.getText().toString();
    }

    private String getSelectedDoctorUsername() {
        AutoCompleteTextView doctorAutoComplete = findViewById(R.id.editTextSearchDoctor);
        if (doctorAutoComplete != null) {
            String selectedDoctor = doctorAutoComplete.getText().toString();
            // În loc să utilizați getItem care poate arunca ArrayIndexOutOfBoundsException,
            // verificați direct textul din AutoCompleteTextView.
            if (!selectedDoctor.isEmpty()) {
                return selectedDoctor;
            }
        }
        return null; // Sau o valoare implicită corespunzătoare.
    }


    private String getSelectedDate() {
        return selectedDate; // Acesta va fi null dacă utilizatorul nu a selectat o dată
    }



    // Metoda pentru a obține ora selectată de utilizator
    private String getSelectedTime() {
        return selectedTime; // Acesta va fi null dacă utilizatorul nu a selectat un timp
    }

    private void validateAndAddAppointment(String username,String doctorUsername, String date, String startTime) {
        String endTime = DB.calculateEndTime(startTime);

        if (doctorUsername == null || date == null || startTime == null || endTime == null) {
            Toast.makeText(this, "Datele introduse sunt invalide.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (DB.isAppointmentAvailable(username,doctorUsername, date, startTime, endTime)) {
            boolean isInserted = DB.addAppointment(username,doctorUsername, date, startTime, endTime);
            if (isInserted) {
                Toast.makeText(this, "Programarea a fost adăugată cu succes.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nu s-a putut adăuga programarea.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Data și ora deja rezervate.", Toast.LENGTH_SHORT).show();
        }
    }
    public class Doctor {
        private String username;
        private String name;

        // Constructor, getteri și setteri
        public Doctor(String username, String name) {
            this.username = username;
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        // Alte metode necesare
    }


}