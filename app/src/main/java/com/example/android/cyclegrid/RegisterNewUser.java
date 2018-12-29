package com.example.android.cyclegrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        final DatabaseReference d1 =
                FirebaseDatabase.getInstance().
                        getReference("users");


        Button b1 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference d1 =
                        FirebaseDatabase.getInstance().
                                getReference("users");

                d1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String uID = "U"+(dataSnapshot.getChildrenCount()+1);
                       // for (DataSnapshot snap: dataSnapshot.getChildren()) {
                        //    uID = "U"+snap.getChildrenCount();
                            //Log.e(snap.getKey(),snap.getChildrenCount() + "");
                       // }


                        final EditText mail = (EditText) findViewById(R.id.editText11);
                        final EditText un = (EditText) findViewById(R.id.editText6);
                        final EditText pass = (EditText) findViewById(R.id.editText7);
                        final EditText conpass = (EditText) findViewById(R.id.editText8);
                        String password = pass.getEditableText().toString();
                        String conpassword = conpass.getEditableText().toString();

                        if(password.equals(conpassword)) {
                            String mailId = mail.getEditableText().toString();
                            String userName = un.getEditableText().toString();
                            String pid = d1.push().getKey();


                            Post p1 = new Post(mailId, userName, password, pid, uID);
                            d1.child(pid).setValue(p1);
                            Toast.makeText(getApplicationContext(),"New User Registered",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"password not matched",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
