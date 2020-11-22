package com.example.armyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.armyapp.db.AppDatabase;
import com.example.armyapp.model.User;
import com.example.armyapp.util.AppExecutors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class add_user extends AppCompatActivity {
    private EditText editText;
    private EditText editTextName;
    private DatePickerDialog.OnDateSetListener setListener;
    private  String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        editText = findViewById(R.id.birth_date_edit_text);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_user.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                editTextName = findViewById(R.id.first_name_edit_text);
                month = month+1;
                date = dayOfMonth+"/"+month+"/"+year;
                editText.setText(date);
                Button saveButton = findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // อ่านค่าจากช่อง first_name_edit_text, last_name_edit_text
                        String name = editTextName.getText().toString();
                        final User user = new User(0, name, date);

                        AppExecutors executors = new AppExecutors();
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() { // worker thread
                                AppDatabase db = AppDatabase.getInstance(add_user.this);
                                db.userDao().addUser(user);
                                finish();
                            }
                        });
                    }
                });
            }
        };
    }
}