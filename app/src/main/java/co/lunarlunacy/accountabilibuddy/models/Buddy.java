package co.lunarlunacy.accountabilibuddy.models;

/**
 * Created by willepstein on 12/20/15.
 */
public class Buddy {

    private String phoneNumber;

    public Buddy(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
