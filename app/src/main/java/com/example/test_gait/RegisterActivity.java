package com.example.test_gait;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email, password,phone,age,date;
    Button btn_register;
    private RadioGroup radio;
    private RadioButton male,female;
    private ImageView calender;
    private TextView selected_gender;

    private static final int DATE_PICKER_ID = 1;

    int year;
    int month;
    int day;

    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        age = findViewById(R.id.age);
        date = findViewById(R.id.date);
        phone=findViewById(R.id.phone);
        calender = findViewById(R.id.calender);
        radio=findViewById(R.id.radio);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        selected_gender=findViewById(R.id.selected_gender);

        auth = FirebaseAuth.getInstance();
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (male.isChecked())
                {
                    selected_gender.setText("Male");
                }
                else
                {
                    selected_gender.setText("Female");
                }
            }
        });
        calender.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_age = age.getText().toString();
                String txt_dob = date.getText().toString();
                String txt_gender = selected_gender.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) ||(TextUtils.isEmpty(txt_phone)) || (TextUtils.isEmpty(txt_age) || (TextUtils.isEmpty(txt_dob) || (TextUtils.isEmpty(txt_gender))))){
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8 ){
                    Toast.makeText(RegisterActivity.this, "password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password, txt_phone, txt_age, txt_dob, txt_gender);
                }
            }
        });
    }

    private void register(final String username, final String email, final String password, final String phone, final String age, final String dob, final String gender){

        Toast.makeText(RegisterActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

        java.util.Calendar calendar= java.util.Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        final String time=format.format(calendar.getTime());
        final String Date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("Email", email);
                            hashMap.put("Age", age);
                            hashMap.put("DOB", dob);
                            hashMap.put("Gender", gender);
                            hashMap.put("Phone Number", phone);
                            hashMap.put("Password", password);
                            hashMap.put("Time",time);
                            hashMap.put("Date",Date);
                            hashMap.put("search", username.toLowerCase());


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SelectDate() {
        final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
        year = c.get(android.icu.util.Calendar.YEAR);
        month = c.get(android.icu.util.Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };
}
