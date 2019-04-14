package com.saloni.doctoroncall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.saloni.doctoroncall.Doctor.DoctorsLogin;
import com.saloni.doctoroncall.Doctor.RegisterDoctor;

public class IamADoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iam_adoctor);
    }

    public void doctorLogin(View view) {
        Intent i = new Intent(this, DoctorsLogin.class);
        startActivity(i);
    }

    public void regDoctor(View view) {
        Intent i = new Intent(this, RegisterDoctor.class);
        startActivity(i);
    }
}
