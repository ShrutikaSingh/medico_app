package com.saloni.doctoroncall.Doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saloni.doctoroncall.MainActivity;
import com.saloni.doctoroncall.R;

import java.util.regex.Pattern;

public class RegisterDoctor extends AppCompatActivity {

    EditText Name;
    EditText Phone;
    Spinner Qualification;
    Spinner Area;
    EditText Email;
    EditText Password;

    ScrollView scrollView;
    ProgressBar pb;
    DatabaseReference myRef;
    MyValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);
        Qualification = findViewById(R.id.select_qiali);
        Area = findViewById(R.id.select_area);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        pb = findViewById(R.id.loadDbProgress);
        pb.setVisibility(View.INVISIBLE);
        scrollView = findViewById(R.id.scrollView_Reg);

        String[] quals = new String[]{"M.B.B.S (General Practitioner)", "BHMS (Homeopathic Doc)",
                "M.B.B.S & Cardiologist", "M.B.B.S & Dermatologist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, quals);
        Qualification.setAdapter(adapter);

        String[] areas = new String[]{"Kothrud", "Warje", "Bavdhan", "Kondhwa", "Wagholi",
                "Aundh", "Shivajinagar", "Dhankawadi"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, areas);
        Area.setAdapter(adapter1);
    }

    public void registerDoctor(android.view.View view) {
        String name = Name.getText().toString();
        String mobile = Phone.getText().toString();
        String qualification = Qualification.getSelectedItem().toString();
        String area = Area.getSelectedItem().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        scrollView.scrollTo(0, 0);
        pb.setVisibility(View.VISIBLE);

        //Testing for data inconsistency
        StringBuilder errorString = new StringBuilder("");
        if (name.isEmpty())
            errorString.append("Name cannot be left empty");
        if (email.isEmpty())
            errorString.append("\nEmail cannot be left empty");
        if (password.isEmpty())
            errorString.append("\nPassword cannot be left empty");
        if (mobile.isEmpty())
            errorString.append("\nPhone No. cannot be left empty");
        if (!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE).matcher(email).find())
            errorString.append("\nEmail is not valid");

        if (!errorString.toString().isEmpty()) {
            Toast.makeText(this, errorString.toString().trim(), Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            finish();
            return;
        }

        //Inserting in Database!
        myRef = FirebaseDatabase.getInstance().getReference("doctors");
        listener = new MyValueEventListener();
        myRef.orderByChild("email").equalTo(email).addValueEventListener(listener);
    }


    class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!dataSnapshot.exists()) {
                //create new user
                String name = Name.getText().toString();
                String mobile = Phone.getText().toString();
                String qualification = Qualification.getSelectedItem().toString();
                String area = Area.getSelectedItem().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                String lat = String.valueOf(MainActivity.lat);
                String lng = String.valueOf(MainActivity.lng);


                Doctor doc = new Doctor(name, mobile, qualification, area, email, password, lat, lng);
                myRef.push().setValue(doc);
                Toast.makeText(RegisterDoctor.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            } else {
                //Already registered user
                Toast.makeText(RegisterDoctor.this, "Already Registered Email", Toast.LENGTH_SHORT).show();
            }
            myRef.removeEventListener(listener);
            pb.setVisibility(View.INVISIBLE);
            finish();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
