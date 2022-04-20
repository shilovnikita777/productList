package com.example.creativeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Vibrator;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    Button buttonSignUp;

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        auth = FirebaseAuth.getInstance();

        editTextName = (EditText)findViewById(R.id.editTextName);

        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true) {
                    MainMenuActivity.open(RegistrationActivity.this);
                }
                else{
                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });
    }

    private boolean correctFields(EditText editTextName,EditText editTextSurname,EditText editTextEmail,EditText editTextPassword){
        if (editTextName.getText().toString().trim().length() == 0) return false;
        else if (editTextSurname.getText().toString().trim().length() == 0) return false;
        else if (editTextEmail.getText().toString().trim().length() == 0) return false;
        else if (editTextPassword.getText().toString().trim().length() == 0) return false;
        else return true;
    }

    public static void open(Context context){
        context.startActivity(new Intent(context,RegistrationActivity.class));
    }
}