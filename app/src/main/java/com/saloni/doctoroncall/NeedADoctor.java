package com.saloni.doctoroncall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.saloni.doctoroncall.Doctor.ListAreaDoctors;

public class NeedADoctor extends AppCompatActivity {

    Spinner Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_adoctor);

        Area = findViewById(R.id.select_area);

        String[] areas = new String[]{"Kothrud", "Warje", "Bavdhan", "Kondhwa", "Wagholi",
                "Aundh", "Shivajinagar", "Dhankawadi"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, areas);
        Area.setAdapter(adp);
    }

    public void findDoctors(View view) {
        Intent i = new Intent(this, ListAreaDoctors.class);
        i.putExtra("area", Area.getSelectedItem().toString());
        startActivity(i);
    }
}
