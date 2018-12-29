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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanAndUnlock extends AppCompatActivity implements View.OnClickListener {
    //View Objects
    private Button buttonScan;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_and_unlock);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();


                    final String qrid = result.getContents().toString();


                    final DatabaseReference d1 =
                            FirebaseDatabase.getInstance().
                                    getReference("Cycles");



                    Query query = d1.orderByChild("cycleID").equalTo(qrid);

                    query.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String cycleid = null;
                            String gps = null;
                            Boolean lock = false;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                cycleid = snapshot.child("cycleID").getValue().toString();
                                gps = snapshot.child("gps").getValue().toString();
                                lock = ((Boolean)snapshot.child("lock").getValue()).booleanValue();
                            }

                            //         System.out.println(snapshot.getKey());
                            //         System.out.println(snapshot.child("userName").getValue(String.class));

                            // undata = dataSnapshot.child("userName").getValue().toString();
                            // passdata = dataSnapshot.child("password").getValue().toString();

                            //Post p1 = dataSnapshot.getValue(Post.class);

                            if (qrid.equals(cycleid) && !lock) {
                                Toast.makeText(getApplicationContext(), "AVAILABLE", Toast.LENGTH_SHORT).show();
                                Intent obj = new Intent(getApplicationContext(), CycleMaps.class);
                                startActivity(obj);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "UNAVAILABLE", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });










                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {

        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
