package co.lunarlunacy.accountabilibuddy.models;

/**
 * Created by willepstein on 12/20/15.
 */
public class Buddy {

    private long id;
    private String name;
    private String phone;
    private Boolean current;

    // Constructors
    public Buddy() {
    }

    public Buddy(String name, String phone, Boolean current) {
        this.name = name;
        this.phone = phone;
        this.current = current;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

}
