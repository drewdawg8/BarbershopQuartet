package com.doublea.barbershopquartet.BackgroundTools;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FirebaseInteraction {
    private FirebaseDatabase database;
    private StorageReference storageRef;
    public FirebaseInteraction(){
        database = FirebaseDatabase.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
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
    public void uploadFile(Uri currentUri, final FileUploadListener uploadListener){
        Uri file = currentUri;
        StorageReference riversRef = storageRef.child("image/" + file.getPath());

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        uploadListener.onSuccess(downloadUrl.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        uploadListener.onFailure();
                    }
                });
    }

    /**
     * @TODO Implement download component
     * @param url
     * @param downloadListener
     */
    public void downloadFile(String url, FileDownloadListener downloadListener){


    }
    /**
     * Barbers
     * @param value object to be written
     */
    public void writeBarber(Barber value){
        String path = "Barbers/" + value.getUid();
        write(path, value);
    }

    /**
     * @TODO implement time slot map function
     * @param timeSlot
     */
    public void writeTimeslot(TimeSlot timeSlot, Barber barber){
        String path = "Barbers/" + barber.getUid() + "/" + timeSlot.getMonth() + "/" + timeSlot.getDay() + "/" +
                timeSlot.map();
        write(path, timeSlot);
    }
    public void writeTimeslot(TimeSlot timeSlot, String barberUid){
        String path = "Barbers/" + barberUid + "/" + timeSlot.getMonth() + "/" + timeSlot.getDay() + "/" +
                timeSlot.map();
        write(path, timeSlot);
    }
}
