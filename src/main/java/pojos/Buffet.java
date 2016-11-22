package pojos;

import javax.persistence.*;

@Entity
@Table(name = "buffets")
public class Buffet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String place;

    @Column(name = "admin", nullable = false, columnDefinition = "bool default false")
    private Boolean isAdmin;

    @Column(name = "complained", nullable = false, columnDefinition = "bool default false")
    private Boolean isComplained;

    @Column(name = "user_id")
    private long userId;

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
    
    public Buffet(String name, String place, Boolean isAdmin, Boolean isComplained,long userId) {
        this.name         = name;
        this.place        = place;
        this.isAdmin      = isAdmin;
        this.isComplained = isComplained;
        this.userId       = userId;
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
    
    public void setUserId(long userId)
    {
        this.userId = userId;
    }
    
    public long getUserId()
    {
        return userId;
    }
}
