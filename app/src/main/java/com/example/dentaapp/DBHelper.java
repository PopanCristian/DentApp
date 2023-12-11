package com.example.dentaapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;

    public static final String DBNAME = "login.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 14);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(username TEXT primary key, password TEXT,email TEXT)");
        MyDB.execSQL("create table appointment(" +
                "id INTEGER primary key AUTOINCREMENT, " +
                "username TEXT, " +
                "doctorusername TEXT, " +
                "data TEXT, " +
                "ora_inceput TEXT, " +
                "ora_sfarsit TEXT, " +
                "FOREIGN KEY(username) REFERENCES users(username)," +
                "FOREIGN KEY(doctorusername) REFERENCES doctors(doctorusername))");
        MyDB.execSQL("create table doctors(doctorusername TEXT primary key, doctorname TEXT, email TEXT, password TEXT, phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists appointment");
        MyDB.execSQL("drop Table if exists doctors");
        onCreate(MyDB);

    }
    public boolean insertData (String username, String password, String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("email",email);
        long result = MyDB.insert("users", null, values);
        if (result == -1) return false;
        else return true;
    }

    public boolean addAppointment(String username,String doctorUsername, String date, String startTime, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("doctorusername", doctorUsername);
        values.put("data", date);
        values.put("ora_inceput", startTime);
        values.put("ora_sfarsit", endTime);

        long result = db.insert("appointment", null, values);
        db.close();

        if (result == -1) {
            return false;
        } else {
            Toast.makeText(context, "Programarea a fost adăugată cu succes.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }



    public boolean isAppointmentAvailable(String username,String doctorUsername, String date, String startTime, String endTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointment WHERE doctorusername = ? AND data = ? AND " +
                "((ora_inceput < ? AND ora_sfarsit > ?) OR (ora_inceput < ? AND ora_sfarsit > ?))";
        Cursor cursor = db.rawQuery(query, new String[]{doctorUsername, date, endTime, startTime, startTime, endTime});

        boolean isAvailable = (cursor.getCount() == 0);
        cursor.close();
        db.close();
        return isAvailable;
    }


    public String calculateEndTime(String startTime) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date startTimeDate = sdf.parse(startTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTimeDate);

            calendar.add(Calendar.HOUR_OF_DAY, 1);

            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean insertDoctor(String doctorusername, String doctorname, String email, String password, String phone) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctorusername",doctorusername);
        contentValues.put("doctorname",doctorname);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        long result = MyDB.insert("doctors", null, contentValues);
        return result != -1;
    }

    public boolean checkusername(String username) {
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("SELECT * from users where username= ?", new String[] {username});
        if(cursor.getCount() > 0 )
            return  true;
        else return false;
    }
    public boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    @SuppressLint("Range")
    public List<String> getAllDoctorNames() {
        List<String> doctorNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT doctorname FROM doctors", null);
        while (cursor.moveToNext()) {
            doctorNames.add(cursor.getString(cursor.getColumnIndex("doctorname")));
        }
        cursor.close();
        return doctorNames;
    }



    public boolean checkusernamepassword (String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from users where username = ? and password=?", new String[] {username,password});
        if(cursor.getCount() > 0)
            return true;
        else return false;
    }
    public boolean checkDoctorUsernamePassword(String doctorusername, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doctors WHERE doctorusername = ? AND password = ?", new String[] {doctorusername, password});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public List<Appointment> getAppointmentsForUser(String username) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM appointment WHERE username = ?", new String[]{username});

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String doctorUsername = cursor.getString(cursor.getColumnIndex("doctorusername"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("data"));
            @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex("ora_inceput"));
            @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex("ora_sfarsit"));
            appointments.add(new Appointment(doctorUsername, date, startTime, endTime));
        }
        Log.d("DBHelper", "Număr de programări returnate: " + appointments.size());

        cursor.close();
        db.close();
        return appointments;
    }
    public List<AppointmentForDoctor> getAppointmentsForDoctor(String doctorUsername) {
        List<AppointmentForDoctor> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointment WHERE doctorusername = ?";
        Cursor cursor = db.rawQuery(query, new String[]{doctorUsername});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String patientUsername = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("data"));
                @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex("ora_inceput"));
                @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex("ora_sfarsit"));
                // Aici asumi că ai o clasă Appointment care poate stoca aceste informații
                appointments.add(new AppointmentForDoctor(patientUsername, date, startTime, endTime));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return appointments;
    }
    @SuppressLint("Range")
    public String getEmailForUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String email = null;
        Cursor cursor = db.query("users", new String[]{"email"}, "username=?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
            cursor.close();
        }
        db.close();

        return email;
    }






}
