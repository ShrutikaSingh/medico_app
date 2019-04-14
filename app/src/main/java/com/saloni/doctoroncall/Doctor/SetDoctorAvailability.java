package com.saloni.doctoroncall.Doctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saloni.doctoroncall.R;

import java.util.ArrayList;

public class SetDoctorAvailability extends AppCompatActivity {

    CheckBox[] Days;
    Spinner Time_from;
    Spinner Time_to;
    Button setAvailability;
    ProgressBar pb;
    ScrollView scrollView;

    String docKey;
    String[] docDetail;
    ArrayList<Integer> docDays;
    int[] docTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_doctor_availability);

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

        Time_from = findViewById(R.id.select_time_from);
        Time_to = findViewById(R.id.select_time_to);
        setAvailability = findViewById(R.id.btn_updateAvailability);
        pb = findViewById(R.id.loadDbProgress);
        pb.setVisibility(View.INVISIBLE);
        scrollView = findViewById(R.id.scrollView_SetAvail);

        Days = new CheckBox[7];
        Days[0] = findViewById(R.id.check_sun);
        Days[1] = findViewById(R.id.check_mon);
        Days[2] = findViewById(R.id.check_tue);
        Days[3] = findViewById(R.id.check_wed);
        Days[4] = findViewById(R.id.check_thu);
        Days[5] = findViewById(R.id.check_fri);
        Days[6] = findViewById(R.id.check_sat);

        for (int i = 0; i < 7; i++) {
            if (docDays.get(i) == 1)
                Days[i].setChecked(true);
            else
                Days[i].setChecked(false);
        }

        String[] timings = new String[]{
                "0000", "0100", "0200", "0300",
                "0400", "0500", "0600", "0700",
                "0800", "0900", "1000", "1100",
                "1200", "1300", "1400", "1500",
                "1600", "1700", "1800", "1900",
                "2000", "2100", "2200", "2300"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, timings);

        Time_from.setAdapter(adapter);
        Time_to.setAdapter(adapter);

        for (int i = 0; i < 24; i++) {
            if (Integer.valueOf(timings[i]) == docTime[0])
                Time_from.setSelection(i);
            if (Integer.valueOf(timings[i]) == docTime[1])
                Time_to.setSelection(i);
        }
    }

    public void updateAvailability(View view) {
        pb.setVisibility(View.VISIBLE);
        scrollView.scrollTo(0, 0);

        ArrayList<Integer> new_days = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if (Days[i].isChecked())
                new_days.add(i, 1);
            else
                new_days.add(i, 0);
        }

        int tf = Integer.valueOf(Time_from.getSelectedItem().toString());
        int tt = Integer.valueOf(Time_to.getSelectedItem().toString());

        Doctor doctor = new Doctor(docDetail, new_days, tf, tt);

        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference("doctors").child(docKey);
        myRef.setValue(doctor);

        Toast.makeText(this, "Time Updated Successfully", Toast.LENGTH_SHORT).show();
        RegisteredDocDetails.docDays = new_days;
        RegisteredDocDetails.docTime = new int[]{tf, tt};
        finish();
    }
}
