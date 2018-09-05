package com.demo.pramod.firebasedatabasedemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText emailEdt, passEdt;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailEdt = findViewById(R.id.emailEdt);
        passEdt = findViewById(R.id.passEdt);
        output = findViewById(R.id.output);
    }

    public void submit(View view) {
        User user = new User();
        user.setEmail(emailEdt.getText().toString());
        user.setPass(passEdt.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Users")
                .push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Constantly listen to any changes done on database
    /*public void fetch(View v){
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String outputData="";
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            User user=dataSnapshot1.getValue(User.class);
                            outputData+="Email:"+user.getEmail()+" Password"+user.getPass();
                            Log.i("ChildEventListener", dataSnapshot1.toString());

                        }
                        output.setText(outputData);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }*/
    /*public void fetch(View v){
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(getApplicationContext(),"Child value is added:"+dataSnapshot.toString(),Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                        Toast.makeText(getApplicationContext(),"Child value is removed "+dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Toast.makeText(getApplicationContext(),"child value is moved "+dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }*/

    public void fetch(View v) {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String outputData = "";
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            User user = dataSnapshot1.getValue(User.class);
                            outputData += "Email:" + user.getEmail() + " Password" + user.getPass();
                            Log.i("ChildEventListener", dataSnapshot1.toString());
                        }
                        Toast.makeText(getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                        output.setText(outputData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}