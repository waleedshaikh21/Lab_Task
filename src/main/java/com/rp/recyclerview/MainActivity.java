package com.rp.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView objectRecyclerView;
    private FirebaseFirestore objectFirebaseFirestore;

    private StatusAdapter objectStatusAdapter;
    EditText nameET,statusET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectXML();
    }

    private void connectXML()
    {
        try
        {
            nameET=findViewById(R.id.nameET);
            statusET=findViewById(R.id.statusET);

            objectFirebaseFirestore=FirebaseFirestore.getInstance();
            objectRecyclerView=findViewById(R.id.RV);

            addValuesToRV();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "connectXML:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addValuesToRV()
    {
        try
        {
            Query objectQuery=objectFirebaseFirestore.collection("Status");
            FirestoreRecyclerOptions<StatusModelClass> options=
                    new FirestoreRecyclerOptions.Builder<StatusModelClass>()
                            .setQuery(objectQuery,StatusModelClass.class)
                            .build();
            objectStatusAdapter=new StatusAdapter(options);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            objectRecyclerView.setAdapter(objectStatusAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "addValuesToRV:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        objectStatusAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        objectStatusAdapter.stopListening();
    }

    public void addValues(View view)
    {
        try
        {
            Map<String,String> dataMap=new HashMap<>();
            dataMap.put("name",nameET.getText().toString());

            dataMap.put("status",statusET.getText().toString());
            objectFirebaseFirestore.collection("Status")
                    .document().set(dataMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            nameET.setText("");
                            statusET.setText("");

                            nameET.requestFocus();
                            Toast.makeText(MainActivity.this, "Published", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Fails to add status:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        catch (Throwable t)
        {
            Toast.makeText(this, "addValues:"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
