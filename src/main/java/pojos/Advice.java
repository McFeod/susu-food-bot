package pojos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "advices")
public class Advice implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    public Advice(Long id, String text) {
        this.id   = id;
        this.text = text;
    }

    public Advice(String text) {
        this.text = text;
    }

    public Advice() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
