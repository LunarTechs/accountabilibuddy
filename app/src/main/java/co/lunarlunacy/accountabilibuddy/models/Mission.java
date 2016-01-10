package co.lunarlunacy.accountabilibuddy.models;

/**
 * Created by willepstein on 1/9/16.
 */
public class Mission {

    private long id;
    private String name;
    private String description;
    private Boolean current;

    public Mission() {
    }

    public Mission(long id, String name, String description, Boolean current) {
        this.id = id;
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

}
