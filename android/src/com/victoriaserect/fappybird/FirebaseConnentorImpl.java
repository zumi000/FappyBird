package com.victoriaserect.fappybird;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class FirebaseConnentorImpl implements FirebaseConnector {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    FirebaseUser anonymFirebaseUser;

    private AndroidLauncher.ContextCommandsImpl contextCommands;

    public FirebaseConnentorImpl(AndroidLauncher.ContextCommandsImpl contextCommands) {
        this.contextCommands = contextCommands;
    }

    public void anonymConnect() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in anonymFirebaseUser's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            anonymFirebaseUser = user;
                        } else {
                            // If sign in fails, display a message to the anonymFirebaseUser.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            anonymFirebaseUser = null;
                        }
                    }
                });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users");
    }

    public String createUserId(UserData user) {
        anonymConnect();
        return mUsersDatabaseReference.push().getKey();
    }

    public void saveNewUser(UserData user) {
        anonymConnect();
        mUsersDatabaseReference.child(user.getFirebaseId()).setValue(user);
    }

    public void updateUser(UserData user) {
        anonymConnect();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users/" + user.getFirebaseId());
        try {
            mUsersDatabaseReference.child("highestScore").setValue(user.getHighestScore());
            mUsersDatabaseReference.child("lastUpdated").setValue(user.getLastUpdated());
            mUsersDatabaseReference.child("lastUpdatedHumanized").setValue(user.getLastUpdatedHumanized());
            mUsersDatabaseReference.child("totalPlayedGames").setValue(user.getTotalPlayedGames());
            mUsersDatabaseReference.child("totalPlayedTime").setValue(user.getTotalPlayedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
