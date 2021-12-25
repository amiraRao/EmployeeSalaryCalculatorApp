package com.example.employeesalaryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Insert extends AppCompatActivity {
    EditText id, name, contact, salary, post;
    String e_id,e_name, e_contact, e_salary, e_post;
    Button save, clear, bac;
    private DatabaseReference databaseReference, myRef;

    private FirebaseDatabase firedb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        id= (EditText) findViewById(R.id.id);
        name= (EditText)findViewById(R.id.name);
        post= (EditText)findViewById(R.id.post);
        salary= (EditText)findViewById(R.id.salary);
        contact= (EditText) findViewById(R.id.contact);
        save= (Button) findViewById(R.id.save);
        clear= (Button) findViewById(R.id.clear);
        bac= (Button) findViewById(R.id.bac);


        databaseReference= FirebaseDatabase.getInstance().getReference("EmployeeData");

        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validOptions()){
                    final String userVal = id.getText().toString().trim();
                    myRef = firedb.getInstance().getReference().child("EmployeeData");
                    Query queries=myRef.orderByChild("empID").equalTo(userVal);
                    queries.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(Insert.this, "This ID already exist! Try another", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                newDAta();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id.setText("");
                name.setText("");
                contact.setText("");
                salary.setText("");
                post.setText("");

            }
        });
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Insert.this, MainActivity.class));
                finish();
            }
        });



    }
    private Boolean validOptions(){
        boolean result= false;

        e_id= id.getText().toString();
        e_name= name.getText().toString();
        e_post= post.getText().toString();
        e_contact= contact.getText().toString();
        e_salary= salary.getText().toString();
        if(e_id.isEmpty() || e_name.isEmpty() || e_post.isEmpty() || e_contact.isEmpty() || e_salary.isEmpty()){
            Toast.makeText(Insert.this, "Enter all required options", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }
    private void newDAta(){
        UserInformation userInformation1 = new UserInformation(e_id, e_name, e_post, e_contact, e_salary);
        databaseReference.child(e_id).setValue(userInformation1);
        Toast.makeText(Insert.this, "Employee Data added Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Insert.this, MainActivity.class));
        finish();
    }
}