package co.lunarlunacy.accountabilibuddy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by willepstein on 12/20/15.
 */
public class Buddy implements Parcelable {

    private long id;
    private String name;
    private String phone;
    private Boolean current;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(name);
        out.writeString(phone);
        out.writeInt(current != null && current ? 1 : 0);
    }

    public static final Parcelable.Creator<Buddy> CREATOR = new Parcelable.Creator<Buddy>() {
        public Buddy createFromParcel(Parcel in) {
            return new Buddy(in);
        }

        public Buddy[] newArray(int size) {
            return new Buddy[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Buddy(Parcel in) {
        id = in.readLong();
        name = in.readString();
        phone = in.readString();
        current = in.readInt() > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
