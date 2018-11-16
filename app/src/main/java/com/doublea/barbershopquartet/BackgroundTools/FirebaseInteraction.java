package com.doublea.barbershopquartet.BackgroundTools;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseInteraction {
    private FirebaseDatabase database;
    public FirebaseInteraction(){
        database = FirebaseDatabase.getInstance();
    }


    /**
     * Reads from firebase database
     * @param path Path to read from
     * @param listen Listener that allows data to be communicated back
     */
    public void read(String path, final FirebaseReadListener listen){
        DatabaseReference currentPath = database.getReference(path);
        currentPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listen.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listen.onFailure();
            }
        });
    }

    /**
     * Reads when called and when change occurs at particular path
     * @param path
     * @param listen
     */
    public void readOnUpdate(String path, final FirebaseReadListener listen){
        DatabaseReference currentPath = database.getReference(path);
        currentPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listen.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listen.onFailure();;
            }
        });
    }
    /**
     * Writes strings to firebase
     * @param path Path of string
     * @param value String to be written
     */
    public void write(String path, String value){
        DatabaseReference currentPath = database.getReference(path);
        currentPath.setValue(value);
    }
    public void write(String path, Object value){
        DatabaseReference currentPath = database.getReference(path);
        currentPath.setValue(value);
    }
}
