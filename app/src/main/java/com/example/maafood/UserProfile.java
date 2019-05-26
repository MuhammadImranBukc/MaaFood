package com.example.maafooD;

public class UserProfile {
    String UserName;
    String UserEmail;
    String UserNumber;

    public UserProfile(){}

    public UserProfile(String userName, String userEmail, String userNumber) {
        UserName = userName;
        UserEmail = userEmail;
        UserNumber = userNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }
}
