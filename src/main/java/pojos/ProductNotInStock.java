package pojos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_not_in_stock")
public class ProductNotInStock implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(targetEntity = Buffet.class, cascade = CascadeType.ALL)
    private Buffet buffet;

    public ProductNotInStock(Long id, Product product, Buffet buffet) {
        this.id      = id;
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductNotInStock(Product product, Buffet buffet) {
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductNotInStock() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buffet getBuffet() {
        return this.buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
