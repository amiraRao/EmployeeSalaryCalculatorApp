package com.example.employeesalaryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Calculate extends AppCompatActivity {
    Button v_view, v_back, v_clear, calculate, save;
    EditText enterID, new_Sal;
    TextView p_salary1, id4, salary4;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;
    String l_name, l_contact, l_post,s, e_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        enterID= (EditText) findViewById(R.id.enterID4);
        new_Sal= (EditText) findViewById(R.id.new_sal);
        p_salary1= (TextView) findViewById(R.id.p_salary1);
        id4= (TextView) findViewById(R.id.id4);
        salary4= (TextView) findViewById(R.id.salary4);
        v_view= (Button) findViewById(R.id.v_view4);
        v_clear= (Button) findViewById(R.id.v_clear4);
        v_back= (Button) findViewById(R.id.v_back4);
        calculate= (Button) findViewById(R.id.calculate4);
        save= (Button) findViewById(R.id.save);

        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();

        v_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userVal = enterID.getText().toString().trim();
                myRef = firedb.getInstance().getReference().child("EmployeeData");
                Query queries=myRef.orderByChild("empID").equalTo(userVal);
                queries.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            UserInformation userInformation = dataSnapshot.child(userVal).getValue(UserInformation.class);
                            String id12= userInformation.getEmpID();
                            if(id12.equals(userVal)){
                                id4.setText(userInformation.getEmpID());
                                p_salary1.setText(userInformation.getEmpSalary());
                                l_name= userInformation.getEmpName();
                                l_contact= userInformation.getEmpContact();
                                l_post= userInformation.getEmpPost();
                            }
                        }else{
                            Toast.makeText(Calculate.this, "There is no employee with this ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Calculate.this, MainActivity.class));
                finish();
            }
        });

        v_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterID.setText("");
                id4.setText("");
                new_Sal.setText("");
                p_salary1.setText("");
                salary4.setText("");
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e_id= enterID.getText().toString().trim();

                String p_sal= p_salary1.getText().toString();
                Float p_sal1 = Float.valueOf(p_sal).floatValue();

                String percen= new_Sal.getText().toString();
                Float per_1= Float.valueOf(percen).floatValue();

                Float cal_percent= p_sal1/100 *per_1;
                Float new_cal_sal= p_sal1 + cal_percent;
                s=String.valueOf(new_cal_sal);
                salary4.setText(s);
                Toast.makeText(Calculate.this, "Salary Calculated", Toast.LENGTH_SHORT).show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation userInformation1 = new UserInformation(e_id,l_name, l_post,l_contact,s);
                myRef.child(e_id).setValue(userInformation1);
                Toast.makeText(Calculate.this, "New data is Saved", Toast.LENGTH_SHORT).show();
                new_Sal.setText("");


            }
        });


    }
}