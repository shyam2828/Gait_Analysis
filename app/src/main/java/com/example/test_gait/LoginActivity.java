package com.example.test_gait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    String u_email;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check if user is null
        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.username_email);



    }
    public void con(View view) {
        String next = email.getText().toString();
        if (TextUtils.isEmpty(next))
        {
            Toast.makeText(LoginActivity.this, "Enter your email to login", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(LoginActivity.this, LoginActivity1.class);
            u_email=email.getText().toString();
            i.putExtra("email",u_email);
            startActivity(i);
        }


    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}