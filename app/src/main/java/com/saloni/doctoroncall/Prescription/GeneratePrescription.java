package com.saloni.doctoroncall.Prescription;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Random;

public class GeneratePrescription extends AppCompatActivity {

    EditText patientName;
    EditText patientFees;
    EditText prescription;

    String pName;
    String pFees;
    String pp;
    String dName;
    String dPhone;
    String prescNumber;
    String docKey;
    String[] docDetail;

    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_prescription);

        patientName = findViewById(R.id.name);
        patientFees = findViewById(R.id.payment);
        prescription = findViewById(R.id.prescription);
        pb = findViewById(R.id.loadDbProgress);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        docKey = extras.getString("docKey");
        assert docKey != null;
        docDetail = extras.getStringArray("docDetail");
        assert docDetail != null;

    }

    public void registerPrescription(View view) {
        pb.setVisibility(View.VISIBLE);

        //Inserting in Database!
        myRef = FirebaseDatabase.getInstance().getReference("prescriptions");
        listener = new MyValueEventListener();
        myRef.addValueEventListener(listener);
    }

    class MyValueEventListener implements ValueEventListener {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //create new prescription
            pName = patientName.getText().toString();
            pp = prescription.getText().toString();
            pFees = patientFees.getText().toString();
            dName = docDetail[0];
            dPhone = docDetail[1];
            prescNumber = String.format("%04d", new Random().nextInt(10001));

            Prescription p = new Prescription(prescNumber, docKey, pName, dName, dPhone, pp, pFees);
            myRef.push().setValue(p);
            Toast.makeText(GeneratePrescription.this, "Prescription Registered Successfully", Toast.LENGTH_SHORT).show();

            //Finishing old activity
            myRef.removeEventListener(listener);
            pb.setVisibility(View.INVISIBLE);
            finish();

            //starting new Activity
            Intent i = new Intent(GeneratePrescription.this, ViewPrescription.class);
            String[] presDetails = new String[]{prescNumber, pName, dName, dPhone, pp, pFees};
            i.putExtra("presDetails", presDetails);
            startActivity(i);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
