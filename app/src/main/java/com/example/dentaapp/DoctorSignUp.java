package com.example.dentaapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorSignUp extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword,editTextDoctorPhone,editTextDoctorUserName;
    AutoCompleteTextView editTextArea;
    Button buttonSignUp;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_sign_up);
        dbHelper = new DBHelper(this);

        editTextName = findViewById(R.id.editTextDoctorName);
        editTextEmail = findViewById(R.id.editTextDoctorEmail);
        editTextPassword = findViewById(R.id.editTextDoctorPassword);
        editTextConfirmPassword = findViewById(R.id.editTextDoctorConfirmPassword);
        editTextDoctorPhone = findViewById(R.id.editTextDoctorPhone);
        editTextDoctorUserName = findViewById(R.id.editTextDoctorUserName);
        editTextArea = findViewById(R.id.editTextArea);
        buttonSignUp = findViewById(R.id.buttonDoctorSignUp);
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

        editTextArea.setAdapter(adapter);
        editTextArea.setThreshold(2);

        editTextArea.setOnItemClickListener((parent, view, position, id) -> {
            String judetSelectat = adapter.getItem(position);
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String repassword = editTextConfirmPassword.getText().toString().trim();
                String username = editTextDoctorUserName.getText().toString().trim();
                String phone = editTextDoctorPhone.getText().toString().trim();
                String location =editTextArea.getText().toString().trim();


                if (validateInputs(username,name, email, password, repassword, phone,location)) {
                    boolean insertSuccessful = dbHelper.insertDoctor(username,name,email, password, phone);

                    if (insertSuccessful) {
                        Toast.makeText(DoctorSignUp.this, "Doctor account successfully created!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoctorSignUp.this, "Error creating doctor account!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateInputs(String username,String name, String email, String password, String repassword, String phone,String location) {

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty() || phone.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Toate câmpurile sunt necesare !", Toast.LENGTH_SHORT).show();
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(yahoo\\.com|gmail\\.com)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            Toast.makeText(this, "Introdu o adresă de email validă!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(this, "Parolele nu corespund !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() != 10) {
            Toast.makeText(this, "Introdu un număr de telefon valid !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
