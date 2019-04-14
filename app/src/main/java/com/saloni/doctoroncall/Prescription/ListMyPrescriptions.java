package com.saloni.doctoroncall.Prescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saloni.doctoroncall.R;

import java.util.ArrayList;

public class ListMyPrescriptions extends AppCompatActivity {

    ListView listView;
    TextView errorText;
    String docKey;

    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;
    ArrayList<Prescription> myPrescriptions;
    ArrayList<String> presKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_prescriptions);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        docKey = extras.getString("docKey");
        assert docKey != null;

        listView = findViewById(R.id.list_patients);
        pb = findViewById(R.id.loadDbProgress);
        errorText = findViewById(R.id.error_text);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Prescription p = myPrescriptions.get(i);
                String presKey = presKeys.get(i);

                //Start Activity with doctor details
                Intent intent = new Intent(ListMyPrescriptions.this, UpdatePrescription.class);
                String[] presDetails = new String[]{
                        p.getpID(),
                        p.getDocKey(),
                        p.getPatientName(),
                        p.getDoctorName(),
                        p.getDoctorPhone(),
                        p.getPrescription(),
                        p.getFees()
                };
                intent.putExtra("presDetails", presDetails);
                intent.putExtra("presKey", presKey);
                startActivity(intent);
            }
        });

        myPrescriptions = new ArrayList<>();
        presKeys = new ArrayList<>();
        listener = new MyValueEventListener();
        myRef = FirebaseDatabase.getInstance().getReference("prescriptions");
        myRef.addValueEventListener(listener);

    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("Data Snapshot:", dataSnapshot.toString());

            myPrescriptions.clear();
            presKeys.clear();

            for (DataSnapshot p : dataSnapshot.getChildren()) {

                Log.e(p.getKey(), p.toString());
                Prescription prescription = p.getValue(Prescription.class);
                assert prescription != null;
                if (prescription.getDocKey().equals(docKey)) {
                    myPrescriptions.add(prescription);
                    presKeys.add(p.getKey());
                }
            }

            String[] names = new String[myPrescriptions.size()];
            for (int i = 0; i < myPrescriptions.size(); i++) {
                names[i] = myPrescriptions.get(i).getPatientName() +
                        "(" + myPrescriptions.get(i).getpID() + ")";
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(ListMyPrescriptions.this,
                    android.R.layout.simple_spinner_dropdown_item, names);
            listView.setAdapter(adapter);

            pb.setVisibility(View.INVISIBLE);
            myRef.removeEventListener(listener);

            //If No Doctors are Available in Area
            if (myPrescriptions.size() == 0)
                errorText.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
