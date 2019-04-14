package com.saloni.doctoroncall.Doctor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.saloni.doctoroncall.R;

import java.util.ArrayList;

public class DoctorDetails extends AppCompatActivity {

    TextView Name;
    TextView Mobile;
    TextView Quali;
    TextView Area;
    TextView Email;
    TextView Days;
    TextView Time;

    String[] docDetail;
    static ArrayList<Integer> docDays;
    static int[] docTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        docDetail = extras.getStringArray("docDetail");
        assert docDetail != null;
        docDays = extras.getIntegerArrayList("docDays");
        assert docDays != null;
        docTime = extras.getIntArray("docTime");
        assert docTime != null;


        Name = findViewById(R.id.name_text);
        Mobile = findViewById(R.id.phone_text);
        Quali = findViewById(R.id.qual_text);
        Area = findViewById(R.id.area_text);
        Email = findViewById(R.id.email_text);
        Days = findViewById(R.id.days_text);
        Time = findViewById(R.id.time_text);

        Name.setText(String.valueOf("Dr. " + docDetail[0]));
        Mobile.setText(docDetail[1]);
        Quali.setText(docDetail[2]);
        Area.setText(docDetail[3]);
        Email.setText(docDetail[4]);

        StringBuilder days_text = new StringBuilder("");
        if (docDays.get(0) == 1)
            days_text.append("Sunday\n");
        if (docDays.get(1) == 1)
            days_text.append("Monday\n");
        if (docDays.get(2) == 1)
            days_text.append("Tuesday\n");
        if (docDays.get(3) == 1)
            days_text.append("Wednesday\n");
        if (docDays.get(4) == 1)
            days_text.append("Thursday\n");
        if (docDays.get(5) == 1)
            days_text.append("Friday\n");
        if (docDays.get(6) == 1)
            days_text.append("Saturday\n");

        Days.setText(days_text.toString().trim());


        StringBuilder time_text = new StringBuilder("");
        int from = docTime[0] / 100;
        if (from < 12)
            time_text.append(String.valueOf(from + " AM"));
        else
            time_text.append(String.valueOf((from - 12) + " PM"));

        time_text.append(" to ");

        int to = docTime[1] / 100;
        if (to < 12)
            time_text.append(String.valueOf(to + " AM"));
        else
            time_text.append(String.valueOf((to - 12) + " PM"));

        Time.setText(time_text.toString());
    }

    public void callDoctor(View view) {
        Toast.makeText(this, "Calling: " + docDetail[1], Toast.LENGTH_SHORT).show();

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + docDetail[1]));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            //Requesting permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    1);
            return;
        }
        startActivity(phoneIntent);
    }
}
