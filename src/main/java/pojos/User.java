package pojos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private Long id;

    private String argument;

    @Column(columnDefinition = "int default 0")
    private Integer rating = 0;

    @Column(columnDefinition = "int default 0")
    @Enumerated(EnumType.ORDINAL)
    private UserState state = UserState.WAITING;

    public User(Long id, String argument, Integer rating, UserState state) {
        this.id       = id;
        this.argument = argument;
        this.rating   = rating;
        this.state    = state;
    }

    public User(String argument, Integer rating, UserState state) {
        this.argument = argument;
        this.rating   = rating;
        this.state    = state;
    }

    public User(Long id) {
        this.id = id;
    }

    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArgument() {
        return this.argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public UserState getState() {
        return this.state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
