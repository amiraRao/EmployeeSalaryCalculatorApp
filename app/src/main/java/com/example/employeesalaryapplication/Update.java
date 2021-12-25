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

public class Update extends AppCompatActivity {
    Button v_view, v_back, v_clear, update;
    String e_id,e_name, e_contact, e_salary, e_post;
    EditText enterID, id1, name1, post1, salary1, contact1;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        enterID= (EditText) findViewById(R.id.enterID1);
        id1 = (EditText) findViewById(R.id.id2);
        name1=(EditText) findViewById(R.id.name2);
        post1= (EditText) findViewById(R.id.post2);
        salary1= (EditText) findViewById(R.id.salary2);
        contact1= (EditText) findViewById(R.id.contact2);
        v_view= (Button) findViewById(R.id.v_view1);
        v_back= (Button) findViewById(R.id.v_back1);
        v_clear= (Button) findViewById(R.id.v_clear1);
        update= (Button) findViewById(R.id.update);


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
                                id1.setText(userInformation.getEmpID());
                                name1.setText(userInformation.getEmpName());
                                post1.setText(userInformation.getEmpPost());
                                salary1.setText(userInformation.getEmpSalary());
                                contact1.setText(userInformation.getEmpContact());
                            }
                        }else{
                            Toast.makeText(Update.this, "There is no employee with this ID", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Update.this, MainActivity.class));
                finish();
            }
        });

        v_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterID.setText("");
                id1.setText("");
                name1.setText("");
                contact1.setText("");
                salary1.setText("");
                post1.setText("");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validOptions()){
                    UserInformation userInformation1 = new UserInformation(e_id, e_name, e_post, e_contact, e_salary);
                    myRef.child(e_id).setValue(userInformation1);
                    Toast.makeText(Update.this, "Employee Data added Successfully", Toast.LENGTH_SHORT).show();

                }
        }
        });
    }

    private Boolean validOptions(){
        boolean result= false;

        e_id= id1.getText().toString();
        e_name= name1.getText().toString();
        e_post= post1.getText().toString();
        e_contact= contact1.getText().toString();
        e_salary= salary1.getText().toString();
        if(e_id.isEmpty() || e_name.isEmpty() || e_post.isEmpty() || e_contact.isEmpty() || e_salary.isEmpty()){
            Toast.makeText(Update.this, "Enter all required options", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }
}