package com.saloni.doctoroncall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.saloni.doctoroncall.Prescription.Prescription;
import com.saloni.doctoroncall.Prescription.ViewPrescription;

public class FindPrescription extends AppCompatActivity {

    EditText presNoView;

    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prescription);

        presNoView = findViewById(R.id.presNo);
        pb = findViewById(R.id.loadDbProgress);

    }

    public void searchPrescription(View view) {
        listener = new MyValueEventListener();
        myRef = FirebaseDatabase.getInstance().getReference("prescriptions");
        myRef.addValueEventListener(listener);
        pb.setVisibility(View.VISIBLE);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("Data Snapshot:", dataSnapshot.toString());

            String pNo = presNoView.getText().toString();
            boolean found = false;

            for (DataSnapshot pres : dataSnapshot.getChildren()) {

                Log.e(pres.getKey(), pres.toString());
                Prescription p = pres.getValue(Prescription.class);
                assert p != null;
                if (p.getpID().equals(pNo)) {
                    //Prescription Found
                    found = true;

                    Intent i = new Intent(FindPrescription.this, ViewPrescription.class);
                    String[] presDetails = new String[]{p.getpID(), p.getPatientName(),
                            p.getDoctorName(), p.getDoctorPhone(), p.getPrescription(), p.getFees()};
                    i.putExtra("presDetails", presDetails);
                    startActivity(i);
                    break;
                }
            }

            //If No Doctors are Available in Area
            if (!found)
                Toast.makeText(FindPrescription.this, "Prescription not found.",
                        Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            myRef.removeEventListener(listener);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
