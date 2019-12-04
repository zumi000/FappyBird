package com.victoriaserect.fappybird;



public interface FirebaseConnector {
    void anonymConnect();
    String createUserId(UserData user);
    void saveNewUser(UserData user);
    void updateUser(UserData user);

}
