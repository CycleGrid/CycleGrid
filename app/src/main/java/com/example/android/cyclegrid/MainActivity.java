package com.example.android.cyclegrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    int ch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button1);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference d1 =
                        FirebaseDatabase.getInstance().
                                getReference("users");

                final EditText username = (EditText) findViewById(R.id.editText);
                final EditText password= (EditText) findViewById(R.id.editText2);
                final String pass = password.getEditableText().toString();
                final String un = username.getEditableText().toString();

                Query query = d1.orderByChild("userName").equalTo(un);

                query.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String undata = null;
                        String passdata = null;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            undata = snapshot.child("userName").getValue().toString();
                            passdata = snapshot.child("password").getValue().toString();
                            System.out.println("USERNAME:" + undata);
                            System.out.println(passdata);
                        }

                        //         System.out.println(snapshot.getKey());
                        //         System.out.println(snapshot.child("userName").getValue(String.class));

                        // undata = dataSnapshot.child("userName").getValue().toString();
                        // passdata = dataSnapshot.child("password").getValue().toString();

                        //Post p1 = dataSnapshot.getValue(Post.class);

                        if (un.equals(undata) && pass.equals(passdata)) {
                            Toast.makeText(getApplicationContext(), "valid", Toast.LENGTH_SHORT).show();
                            Intent obj = new Intent(getApplicationContext(), ScanAndUnlock.class);
                            startActivity(obj);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "not valid user", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });
    }

    public void openRegUser(View view) {
        Intent o = new Intent(getApplicationContext(), RegisterNewUser.class);
        startActivity(o);
    }

}