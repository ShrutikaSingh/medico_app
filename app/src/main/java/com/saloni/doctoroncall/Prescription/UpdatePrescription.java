package com.saloni.doctoroncall.Prescription;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saloni.doctoroncall.R;

public class UpdatePrescription extends AppCompatActivity {

    EditText patientNameView;
    EditText prescriptionView;
    EditText feesView;
    ProgressBar pb;
    ScrollView scrollView;

    String[] presDetails;
    String presKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prescription);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        presDetails = extras.getStringArray("presDetails");
        assert presDetails != null;
        presKey = extras.getString("presKey");
        assert presKey != null;

        patientNameView = findViewById(R.id.name);
        prescriptionView = findViewById(R.id.prescription);
        feesView = findViewById(R.id.payment);
        pb = findViewById(R.id.loadDbProgress);
        scrollView = findViewById(R.id.scrollView_UpdReg);

        patientNameView.setText(presDetails[2]);
        prescriptionView.setText(presDetails[5]);
        feesView.setText(presDetails[6]);

    }

    public void updatePrescription(View view) {
        pb.setVisibility(View.VISIBLE);
        scrollView.scrollTo(0, 0);

        Prescription prescription = new Prescription(
                presDetails[0],
                presDetails[1],
                patientNameView.getText().toString(),
                presDetails[3],
                presDetails[4],
                prescriptionView.getText().toString(),
                feesView.getText().toString()
        );

        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference("prescriptions").child(presKey);
        myRef.setValue(prescription);

        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    public void deletePrescription(View view) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("prescriptions").child(presKey);
        myRef.removeValue();

        Toast.makeText(this,
                "Prescription record deleted successfully.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

}
