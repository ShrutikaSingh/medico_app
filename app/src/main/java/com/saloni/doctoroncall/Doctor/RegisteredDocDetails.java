package com.saloni.doctoroncall.Doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saloni.doctoroncall.Prescription.GeneratePrescription;
import com.saloni.doctoroncall.Prescription.ListMyPrescriptions;
import com.saloni.doctoroncall.R;

import java.util.ArrayList;

public class RegisteredDocDetails extends AppCompatActivity {

    TextView Name;
    TextView Mobile;
    TextView Quali;
    TextView Area;
    TextView Email;

    Button setAvailability;
    Button delete;

    String docKey;
    String[] docDetail;
    static ArrayList<Integer> docDays;
    static int[] docTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_doc_details);

        Name = findViewById(R.id.name_text);
        Mobile = findViewById(R.id.phone_text);
        Quali = findViewById(R.id.qual_text);
        Area = findViewById(R.id.area_text);
        Email = findViewById(R.id.email_text);
        setAvailability = findViewById(R.id.btn_setAvailability);
        delete = findViewById(R.id.btn_delete);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        docKey = extras.getString("docKey");
        assert docKey != null;
        docDetail = extras.getStringArray("docDetail");
        assert docDetail != null;
        docDays = extras.getIntegerArrayList("docDays");
        assert docDays != null;
        docTime = extras.getIntArray("docTime");
        assert docTime != null;

        Name.setText(docDetail[0]);
        Mobile.setText(docDetail[1]);
        Quali.setText(docDetail[2]);
        Area.setText(docDetail[3]);
        Email.setText(docDetail[4]);
    }

    public void setAvailability(View view) {
        //Start new Activity
        Intent i = new Intent(this, SetDoctorAvailability.class);
        i.putExtra("docKey", docKey);
        i.putExtra("docDetail", docDetail);
        i.putExtra("docTime", docTime);
        i.putExtra("docDays", docDays);
        startActivity(i);
    }

    public void deleteDoctor(View view) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("doctors").child(docKey);
        myRef.removeValue();

        Toast.makeText(this,
                "Your Record is Deleted\nThanks for contribution :)",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    public void generatePrescription(View view) {
        Intent i = new Intent(this, GeneratePrescription.class);
        i.putExtra("docKey", docKey);
        i.putExtra("docDetail", docDetail);
        startActivity(i);
    }

    public void viewMyPrescriptions(View view) {
        Intent i = new Intent(this, ListMyPrescriptions.class);
        i.putExtra("docKey", docKey);
        startActivity(i);
    }
}
