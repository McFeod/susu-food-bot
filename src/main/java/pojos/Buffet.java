package pojos;

import javax.persistence.*;

@Entity
@Table(name = "buffets")
public class Buffet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String place;

    @Column(name = "admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "complained", nullable = false)
    private Boolean isComplained;

    public Buffet(Long id, String name, String place, Boolean isAdmin, Boolean isComplained) {
        this.id           = id;
        this.name         = name;
        this.place        = place;
        this.isAdmin      = isAdmin;
        this.isComplained = isComplained;
    }

    public Buffet(Long id, String name, Boolean isAdmin, Boolean isComplained) {
        this.id           = id;
        this.name         = name;
        this.isAdmin      = isAdmin;
        this.isComplained = isComplained;
    }

    public Buffet(String name, String place, Boolean isAdmin, Boolean isComplained) {
        this.name         = name;
        this.place        = place;
        this.isAdmin      = isAdmin;
        this.isComplained = isComplained;
    }

    public Buffet(String name, Boolean isAdmin, Boolean isComplained) {
        this.name         = name;
        this.isAdmin      = isAdmin;
        this.isComplained = isComplained;
    }

    public Buffet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsComplained() {
        return this.isComplained;
    }

    public void setIsComplained(Boolean isComplained) {
        this.isComplained = isComplained;
    }
}
