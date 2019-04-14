package com.saloni.doctoroncall.Doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saloni.doctoroncall.MapsActivity;
import com.saloni.doctoroncall.R;

import java.util.ArrayList;

public class ListAreaDoctors extends AppCompatActivity {

    ListView listView;
    TextView listHeading;
    String area;
    TextView errorText;
    Button showOnMap;

    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;
    public static ArrayList<Doctor> areaDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_area_doctors);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        area = extras.getString("area");
        assert area != null;


        listHeading = findViewById(R.id.list_docs_heading);
        listView = findViewById(R.id.list_doctors);
        pb = findViewById(R.id.loadDbProgress);
        errorText = findViewById(R.id.error_text);
        showOnMap = findViewById(R.id.btn_showOnMap);
        listHeading.setText(String.valueOf("Doctors in \"" + area + "\""));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Doctor doc = areaDoctors.get(i);

                //Start Activity with doctor details
                Intent intent = new Intent(ListAreaDoctors.this, DoctorDetails.class);

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

                intent.putExtra("docDetail", docDetail);
                intent.putExtra("docTime", new int[]{doc.getHrs_from(), doc.getHrs_to()});
                intent.putExtra("docDays", doc.getDays());
                startActivity(intent);
            }
        });

        areaDoctors = new ArrayList<>();
        listener = new MyValueEventListener();
        myRef = FirebaseDatabase.getInstance().getReference("doctors");
        myRef.addValueEventListener(listener);
    }

    public void showOnMap(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("Data Snapshot:", dataSnapshot.toString());

            areaDoctors.clear();

            for (DataSnapshot doctor : dataSnapshot.getChildren()) {

                Log.e(doctor.getKey(), doctor.toString());
                Doctor doc = doctor.getValue(Doctor.class);
                assert doc != null;
                if (doc.getArea().equals(area)) {
                    areaDoctors.add(doc);
                }
            }

            String[] names = new String[areaDoctors.size()];
            for (int i = 0; i < areaDoctors.size(); i++) {
                names[i] = areaDoctors.get(i).getName();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(ListAreaDoctors.this,
                    android.R.layout.simple_spinner_dropdown_item, names);
            listView.setAdapter(adapter);

            pb.setVisibility(View.INVISIBLE);
            myRef.removeEventListener(listener);

            //If No Doctors are Available in Area
            if (areaDoctors.size() == 0)
                errorText.setVisibility(View.VISIBLE);
            else
                showOnMap.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
