package com.saloni.doctoroncall.Prescription;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.saloni.doctoroncall.R;

public class ViewPrescription extends AppCompatActivity {

    String[] presDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        presDetails = extras.getStringArray("presDetails");
        assert presDetails != null;

        TextView pNo = findViewById(R.id.presNo_text);
        TextView heading = findViewById(R.id.pname_text);
        TextView pName = findViewById(R.id.pname_text2);
        TextView dName = findViewById(R.id.dname_text);
        TextView dPhone = findViewById(R.id.dphone_text);
        TextView presc = findViewById(R.id.presc_text);
        TextView fees = findViewById(R.id.fees_text);

        pNo.setText(presDetails[0]);
        heading.setText(presDetails[1]);
        pName.setText(presDetails[1]);
        dName.setText(presDetails[2]);
        dPhone.setText(presDetails[3]);
        presc.setText(presDetails[4]);
        fees.setText(presDetails[5]);
    }
}
