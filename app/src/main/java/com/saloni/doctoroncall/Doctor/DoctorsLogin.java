package com.saloni.doctoroncall.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saloni.doctoroncall.R;

public class DoctorsLogin extends AppCompatActivity {

    EditText Email;
    EditText Password;

    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_login);

        Email = findViewById(R.id.doctors_email);
        Password = findViewById(R.id.doctors_pswd);
        pb = findViewById(R.id.loadDbProgress);
        pb.setVisibility(View.INVISIBLE);
    }

    public void loginInto(View view) {
        pb.setVisibility(View.VISIBLE);
        //Inserting in Database!
        myRef = FirebaseDatabase.getInstance().getReference("doctors");
        listener = new MyValueEventListener();
        myRef.addValueEventListener(listener);

    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("Data Snapshot:", dataSnapshot.toString());

            String email = Email.getText().toString();
            String password = Password.getText().toString();
            boolean login_flag = false;

            for (DataSnapshot doctor : dataSnapshot.getChildren()) {

                Log.e(doctor.getKey(), doctor.toString());
                Doctor doc = doctor.getValue(Doctor.class);
                assert doc != null;
                if (doc.getEmail().equals(email) && doc.getPassword().equals(password)) {
                    Toast.makeText(DoctorsLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    login_flag = true;
                    pb.setVisibility(View.INVISIBLE);
                    myRef.removeEventListener(listener);
                    finish();

                    //Start new Activity
                    Intent i = new Intent(DoctorsLogin.this, RegisteredDocDetails.class);

                    String[] docDetail = new String[]{
                            doc.getName(),
                            doc.getMobile(),
                            doc.getQualification(),
                            doc.getArea(),
                            doc.getEmail(),
                            doc.getPassword(),
                            doc.getLatitude(),
                            doc.getLongitude()
                    };

                    i.putExtra("docDetail", docDetail);
                    i.putExtra("docTime", new int[]{doc.getHrs_from(), doc.getHrs_to()});
                    i.putExtra("docDays", doc.getDays());
                    i.putExtra("docKey", doctor.getKey());
                    startActivity(i);
                }
            }

            if (!login_flag) {
                Toast.makeText(DoctorsLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.INVISIBLE);
                myRef.removeEventListener(listener);
                finish();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
