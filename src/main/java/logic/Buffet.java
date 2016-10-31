package logic;


public class Buffet {

    private long id;
    private String name;
    private Boolean isAdmin;
    private Boolean isComplained;

    public Buffet() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsComplained(Boolean isComplained) {
        this.isComplained = isComplained;
    }

    public Boolean getIsComplained() {
        return isComplained;
    }
}
