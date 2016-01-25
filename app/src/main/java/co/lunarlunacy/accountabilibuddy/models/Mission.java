package co.lunarlunacy.accountabilibuddy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by willepstein on 1/9/16.
 */
public class Mission implements Parcelable {

    private long id;
    private String name;
    private String description;
    private Boolean current;

    public Mission() {
    }

    public Mission(String name, String description, Boolean current) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        out.writeString(description);
        out.writeInt(current != null && current ? 1 : 0);
    }

    public static final Parcelable.Creator<Mission> CREATOR = new Parcelable.Creator<Mission>() {
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Mission(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        current = in.readInt() > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

}
