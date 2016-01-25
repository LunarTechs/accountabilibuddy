package co.lunarlunacy.accountabilibuddy.utils;

/**
 * Created by willepstein on 12/23/15.
 */
public enum Tags {

    PHONE_NUMBER("co.lunarlunacy.accountabilibuddy.PHONE_NUMBER", "PHONE_NUMBER"),
    MESSAGE("co.lunarlunacy.accountabilibuddy.MESSAGE", "MESSAGE"),
    CURRENT_BUDDY("co.lunarlunacy.accountabilibuddy.CURRENT_BUDDY", "CURRENT_BUDDY"),
    CURRENT_MISSION("co.lunarlunacy.accountabilibuddy.CURRENT_MISSION", "CURRENT_MISSION");

    private final String longName;
    private final String shortName;

    Tags(String longName, String shortName) {
        this.longName = longName;
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }
}
